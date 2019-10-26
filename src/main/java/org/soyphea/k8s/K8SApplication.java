    package org.soyphea.k8s;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@ConfigurationProperties
public class K8SApplication implements CommandLineRunner {



    Logger logger = LoggerFactory.getLogger(K8SApplication.class);

    @Value("${use.name:default-name}")
    String userName;

    public static void main(String[] args) {
        SpringApplication.run(K8SApplication.class, args);
    }

    @GetMapping("/k8s/{name}")
    public String k8sGreeting(@PathVariable("name") String name) {

        logger.info("Got the request with name:{}",name);
        return String.format("Hi %s- I am a SpringBoot Application run inside Kubernetes",name);

    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("User name fetched from k8s  ConfigMap <spring-boot-k8s> is :{}",userName);
    }
}
