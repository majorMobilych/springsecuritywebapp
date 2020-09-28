package com.web.app.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Configuration class, storing all necessary beans to connect to database.
 */
@Configuration
/*
 *  EXPLANATION: @EnableJpaRepositories enables JPA repositories. Will scan the package of the annotated
 *              configuration class for Spring Data repositories by default, so set package manually.
 *              Also, set entityManagerFactory and transactionManager references.
 */
@EnableJpaRepositories
        (
                basePackages = "com.web.app",
                entityManagerFactoryRef = "localContainerEntityManagerFactoryBean",
                transactionManagerRef = "jpaTransactionManager"
        )
/*
 *  EXPLANATION: A transaction is a unit of work that is performed against a database.
 *              Transactions are units or sequences of work accomplished in a logical order,
 *              whether in a manual fashion by a user.
 *              In other words, transaction is a group of sequential commands that can be executed in order
 *              and as a whole, or not at all. To manage transactions, we should define a transaction manager.
 *              @EnableTransactionManagement enables transaction management.
 *
 *  NOTE:        I'm using Spring Data, and calls on Spring Data repositories are, by default,
 *              surrounded by a transaction, even without @EnableTransactionManagement.
 *              But, nonetheless, I use @EnableTransactionManagement to show I'm using transactions.
 */
@EnableTransactionManagement
/*
 *  EXPLANATION: @PropertySource resolves property-file, which contains all necessary database properties:
 *              db.driver:
 *                  Driver is an adaptor program is required for making a connection to another system
 *                  of different type. In our case, it's PostgreSQL database,
 *              db.url:
 *                  A URL is a reference to a web resource (it's address) that specifies its location
 *                  on a computer network and a mechanism for retrieving it,
 *              db.username and
 *              db.password
 *                  Grants access only to one user (the database admin);
 *
 *              Also, this file contains hibernate-specified properties:
 *                  hibernate.packages_to_scan:
 *                      This property allows to resolve all classes, annotated as @Entity 'es in a specified packages.
 *                      Set the root folder in case of project folder-structure changes,
 *                  hibernate.dialect:
 *                      Dialect of SQL implemented by a particular database,
 *                  hibernate.show_sql:
 *                      Enable logging of generated (fake) SQL to the console,
 *                  hibernate.hbm2ddl.auto:
 *                      Each time context is updated (i.e., app is started), there will be executed a specified action:
 *                      CREATE or DROP or UPDATE or another (for mor info, go to 'Action' enum in
 *                      org.hibernate.tool.schema package). We set 'none'.
 *
 *  NOTE:        Some properties are duplicated from application.properties file, but I prefer to store all properties
 *              in a single file for each configuration class.
 */
@PropertySource("properties/db/springdata.properties")
public class SpringDataConfig {

    /*
     *  EXPLANATION: @Value("${...}") injects declared property from file, declared in @PropertySource("...")
     *              (namely, springdata.properties in our case).
     */
    @Value("${db.driver}")
    private String driver;

    /*
     *  EXPLANATION: Inject property "db.driver" from springdata.properties file.
     */
    @Value("${db.url}")
    private String url;

    /*
     *  EXPLANATION: Inject property "db.username" from springdata.properties file.
     */
    @Value("${db.username}")
    private String username;

    /*
     *  EXPLANATION: Inject property "db.password" from springdata.properties file.
     */
    @Value("${db.password}")
    private String password;

    /*
     *  EXPLANATION: Inject property "hibernate.packages_to_scan" from springdata.properties file.
     */
    @Value("${hibernate.packages_to_scan}")
    private String packages_to_scan;

    /*
     *  EXPLANATION: Inject property "hibernate.dialect" from springdata.properties file.
     */
    @Value("${hibernate.dialect}")
    private String dialect;

    /*
     *  EXPLANATION: Inject property "hibernate.show_sql" from springdata.properties file.
     */
    @Value("${hibernate.show_sql}")
    private String show_sql;

    /*
     *  EXPLANATION: Inject property "hibernate.hbm2ddl.auto" from springdata.properties file.
     */
    @Value("${hibernate.hbm2ddl.auto}")
    private String enable_lazy_load_no_trans;

    /*
     *  EXPLANATION: Create datasource for database connection and set all required properties for datasource.
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();

        driverManagerDataSource.setDriverClassName(driver);
        driverManagerDataSource.setUrl(url);
        driverManagerDataSource.setUsername(username);
        driverManagerDataSource.setPassword(password);

        return driverManagerDataSource;
    }

    /*
     *  EXPLANATION: Define hibernate properties and return the properties-map.
     */
    @Bean
    public Properties properties() {
        Properties hibernateProperties = new Properties();

        hibernateProperties.put("hibernate.dialect", dialect);
        hibernateProperties.put("hibernate.show_sql", show_sql);
        hibernateProperties.put("hibernate.hbm2ddl.auto", enable_lazy_load_no_trans);

        return hibernateProperties;
    }

    /*
     *  EXPLANATION: This entity-manager-factory bean is used for combining all data to connect to database.
     */
    @Bean
    /*
     *  EXPLANATION: @Autowired above methods means that annotated methods may have
     *              an arbitrary name and any number of arguments; each of those arguments will be autowired
     *              with a matching bean in the Spring container.
     */
    @Autowired
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(DataSource dataSource,
                                                                                         Properties properties) {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean =
                new LocalContainerEntityManagerFactoryBean();

        localContainerEntityManagerFactoryBean.setDataSource(dataSource);
        localContainerEntityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        localContainerEntityManagerFactoryBean.setJpaProperties(properties);
        localContainerEntityManagerFactoryBean.setPackagesToScan(packages_to_scan);

        return localContainerEntityManagerFactoryBean;
    }

    /*
     *  EXPLANATION: This bean is used for transactions handling.
     *              There we need to set the EntityManagerFactory that this TransactionManager should manage
     *              transactions.
     */
    @Bean
    /*
     *  ...
     */
    @Autowired
    public PlatformTransactionManager jpaTransactionManager(LocalContainerEntityManagerFactoryBean
                                                                    localContainerEntityManagerFactoryBean) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();

        jpaTransactionManager.setEntityManagerFactory(localContainerEntityManagerFactoryBean.getObject());

        return jpaTransactionManager;
    }
}
