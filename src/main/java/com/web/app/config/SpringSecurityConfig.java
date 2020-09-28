package com.web.app.config;

import com.web.app.security.newjwt.JwtUsernamePasswordAuthenticationFilter;
import com.web.app.security.newjwt.JwtVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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

    /*
     *  EXPLANATION: Specify AuthenticationProvider There we set passwordEncoder
     *              {from our PasswordEncodingConfig class},
     *              our custom UserDetailsService {JwtUserDetailsService class}.
     *
     *  NOTE:        Create a bean out of this method, because we may want to use it somewhere else.
     */
    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);

        return daoAuthenticationProvider;
    }

    /*
     *  EXPLANATION: Set AuthenticationProvider in AuthenticationManagerBuilder.
     *
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(
                        new JwtUsernamePasswordAuthenticationFilter
                                (
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
                        "/all/login"
                ).permitAll()

                /* @POST api */
                .antMatchers("/all/login").permitAll()

                /*  All other requests should be available for authenticated users(for those, who entered
                 *  username and password)
                 */
                .anyRequest().authenticated();
    }
}
