package org.soyphea.k8s;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soyphea.k8s.config.UserConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class K8SApplication implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(K8SApplication.class);

    @Autowired
    UserConfig userConfig;

    public static void main(String[] args) {
        SpringApplication.run(K8SApplication.class, args);
    }

    @GetMapping("/k8s/{name}")
    public String k8sGreeting(@PathVariable("name") String name) {

        logger.info("Got the request with name:{}", name);
        return String.format("Hi %s- I am ConfigMap running in side k8s with value %s", name,userConfig);

    }

    @Override
    public void run(String... args) throws Exception {
        logger.info(" Fetched user from k8s ConfigMap <spring-boot-k8s> is :{}", userConfig);
    }
}
