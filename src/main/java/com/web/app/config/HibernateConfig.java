package com.web.app.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

//TODO: поменял конфиг.
@Configuration
@EnableTransactionManagement
@Slf4j
@Getter
public class HibernateConfig {

    //TODO: хотел для всех этих полей использовать @Value(...), но она не работает - надо прописывать бин
    // PropertyPlaceholderConfigurer, но он какой-то тупой(прописывается через xml) - раньше он работал из-за спрингбута
    // а теперь у нас чистый спринг? + как прописать PropertyPlaceholderConfigurer не через xml?
    private static String driver;

    private static String url;

    private static String username;

    private static String password;

    private static String packages_to_scan;

    private static String dialect;

    private static String show_sql;

    private static String enable_lazy_load_no_trans;

    static {
        try {
            Properties properties = new Properties();
            properties.load(Objects.requireNonNull(HibernateConfig.class.getClassLoader()
                    .getResourceAsStream("properties/db/hibernate.properties")));

            driver = properties.getProperty("driver");
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
            packages_to_scan = properties.getProperty("packages_to_scan");
            dialect = properties.getProperty("dialect");
            show_sql = properties.getProperty("show_sql");
            enable_lazy_load_no_trans = properties.getProperty("enable_lazy_load_no_trans");

        } catch (IOException e) {
            log.error("IN initializing LocalSessionFactoryBean - cant't load properties from property-file");
            e.printStackTrace();
        }
    }

    private DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    private Properties getHibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", dialect);
        hibernateProperties.put("hibernate.show_sql", show_sql);
        hibernateProperties.put("hibernate.hbm2ddl.auto", enable_lazy_load_no_trans);
        return hibernateProperties;
    }

    @Bean
    public SessionFactory createSessionFactory() {
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();

        localSessionFactoryBean.setDataSource(getDataSource());
        localSessionFactoryBean.setPackagesToScan(packages_to_scan);
        localSessionFactoryBean.setHibernateProperties(getHibernateProperties());
        //TODO: Вызываем метод afterPropertiesSet() - обозначем, что передали все проперти и на их основе генерим
        // SessionFactory (Только этот метод ее генерит, см. доку). Получается бан. Почему?
        try {
            localSessionFactoryBean.afterPropertiesSet();
        } catch (IOException e) {
            log.error("IN createSessionFactory - got IOException while creating localSessionFactoryBean");
            e.printStackTrace();
        }
        return localSessionFactoryBean.getObject();
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(createSessionFactory());
        return transactionManager;
    }
}
