package dev.javiervs.inboxapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Bean;

import java.nio.file.Path;

@SpringBootApplication
public class InboxAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(InboxAppApplication.class, args);
    }

    /**
     * This is necessary to have the Spring Boot app use the Astra secure bundle to connect to the database.
     * @param astraProperties
     * @return
     */
    @Bean
    public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties astraProperties) {
        Path bundle = astraProperties.getSecureConnectBundle().toPath();
        return builder -> builder.withCloudSecureConnectBundle(bundle);
    }
}
