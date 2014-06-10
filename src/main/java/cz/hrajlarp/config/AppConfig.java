package cz.hrajlarp.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailMessage;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


@EnableWebMvc
@Configuration
@ComponentScan({ "cz.hrajlarp" })
@EnableTransactionManagement
@Import({ SecurityConfig.class })
@PropertySource("classpath:jdbc.properties, mail.properties")
@EnableScheduling
public class AppConfig extends WebMvcConfigurerAdapter implements SchedulingConfigurer {
    @Inject
    private Environment env;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/img/**").addResourceLocations("/assets/img/");
        registry.addResourceHandler("/style/**").addResourceLocations("/assets/style/");
        registry.addResourceHandler("/js/**").addResourceLocations("/assets/js/");
        registry.addResourceHandler("/fonts/**").addResourceLocations("/assets/fonts/");
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setPassword(env.getProperty("db.password"));
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUsername(env.getProperty("db.username"));

        dataSource.setInitialSize(2);
        dataSource.setMaxActive(10);
        dataSource.setDriverClassName("org.postgresql.Driver");
        return dataSource;
    }

    @Bean
    public SessionFactory sessionFactory() {
        LocalSessionFactoryBuilder builder =
                new LocalSessionFactoryBuilder(dataSource());
        builder.scanPackages("cz.hrajlarp")
                .addProperties(getHibernateProperties());

        return builder.buildSessionFactory();
    }

    private Properties getHibernateProperties() {
        Properties prop = new Properties();
        prop.put("hibernate.format_sql", "true");
        prop.put("hibernate.show_sql", "true");
        prop.put("hibernate.hbm2ddl.auto", "update");
        prop.put("hibernate.dialect",
                "org.hibernate.dialect.PostgreSQL82Dialect");
        return prop;
    }

    //Create a transaction manager
    @Bean
    public HibernateTransactionManager txManager() {
        return new HibernateTransactionManager(sessionFactory());
    }

    @Bean
    public MultipartResolver multipartResolver() {
        int MAX_SIZE = 20971520;
        int MAX_IN_MEMORY = 1048576;
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(MAX_SIZE);
        resolver.setMaxInMemorySize(MAX_IN_MEMORY);
        return resolver;
    }

    // Resolve logical view names to .jsp resources in the /WEB-INF/views directory
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Bean
    public MailSender mailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(env.getProperty("mail.host"));
        javaMailSender.setPort(Integer.parseInt(env.getProperty("mail.port")));
        javaMailSender.setUsername(env.getProperty("mail.username"));
        javaMailSender.setPassword(env.getProperty("mail.password"));
        javaMailSender.setJavaMailProperties(getMailProperties());
        return javaMailSender;
    }

    @Bean
    public MailMessage templateMessage() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(env.getProperty("mail.from"));
        mailMessage.setSubject(env.getProperty("mail.subject"));
        return mailMessage;
    }

    private Properties getMailProperties() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", env.getProperty("mail.smtp.auth"));
        prop.put("mail.smtp.starttls.enable", env.getProperty("mail.smtp.starttls.enable"));
        return prop;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(taskExecutor());
    }

    @Bean(destroyMethod="shutdown")
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(100);
    }
}