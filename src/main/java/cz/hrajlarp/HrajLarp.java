package cz.hrajlarp;

import cz.hrajlarp.service.calendar.Event;
import cz.hrajlarp.storage.Adapter;
import cz.hrajlarp.storage.JpaAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@SpringBootApplication
public class HrajLarp {
    @Bean
    public MailSender mailSender() {
        return new JavaMailSenderImpl();
    }

    @Bean
    public SimpleMailMessage simpleMailMessage() {
        return new SimpleMailMessage();
    }

    @Bean
    public Adapter jpaAdapter() {
        return new JpaAdapter<>();
    }

    public static void main(String[] args) {
        SpringApplication.run(HrajLarp.class, args);
    }
}
