package com.web.app.config;

import com.web.app.security.jwt.JwtUsernamePasswordAuthenticationFilter;
import com.web.app.security.jwt.JwtVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class, setting up the app's security config.
 */
@Configuration
/*
 *  EXPLANATION: @EnableWebSecurity enables to have the Spring Security configuration defined in any
 *              {@link WebSecurityConfigurer} or more likely by extending the {@link WebSecurityConfigurerAdapter} base
 *              class and overriding individual methods.
 */
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SpringSecurityConfig(
            /*
             *  EXPLANATION: @Qualifier("jwtUserDetailsService") defines, which UserDetailsService will be passed as
             *              param to this constructor(there are several classes, implementing UserDetailsService,
             *              running at the same time), we need our custom implementation of UserDetailsService.
             */
            @Qualifier("jwtUserDetailsService") UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(
                        new JwtUsernamePasswordAuthenticationFilter(
                                        /*
                                         *  NOTE: this method called from WebSecurityConfigurerAdapter class
                                         */
                                        authenticationManager()
                                )
                )
                .addFilterAfter(new JwtVerifier(), JwtUsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()

                /* resources */
                .antMatchers(
                        "/css/**",
                        "/js/**"
                ).permitAll()

                /* @GET api */
                .antMatchers(
                        "/welcome",
                        "/login"
                ).permitAll()

                /* @POST api */
                .antMatchers("/all/login").permitAll()

                /*  All other requests should be available for authenticated users(for those, who entered
                 *  username and password)
                 */
                .anyRequest().authenticated();
    }
}
