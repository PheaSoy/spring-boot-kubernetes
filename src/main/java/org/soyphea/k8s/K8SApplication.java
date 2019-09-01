    package org.soyphea.k8s;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class K8SApplication {

    String name = K8SApplication.class.getName();

    Logger logger = LoggerFactory.getLogger(name);

    public static void main(String[] args) {
        SpringApplication.run(K8SApplication.class, args);
    }

    @GetMapping("/k8s/{name}")
    public String k8sGreeting(@PathVariable("name") String name) {

        logger.info("Got the request with name:{}",name);
        return String.format("Hi %s- I am a SpringBoot Application run inside Kubernetes",name);

    }
}
