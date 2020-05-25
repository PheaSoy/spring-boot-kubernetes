package org.soyphea.k8s.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soyphea.k8s.config.UserConfig;
import org.soyphea.k8s.domain.User;
import org.soyphea.k8s.srevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {

    Logger logger = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private UserConfig userConfig;

    @Autowired
    private UserService userService;

    public Controller(){
        logger.info("Initialised Bean.");
    }
    @GetMapping("/k8s/{name}")
    public String k8sGreeting(@PathVariable("name") String name) {

        logger.info("Got the request with name:{}", name);
        return String.format("Hi %s- I am ConfigMap running in side k8s with value %s", name,userConfig);
    }

    @GetMapping("/users/{contain_name}")
    public List<User> getUsersByContainName(@PathVariable("contain_name") String containName){
        return userService.getUser(containName);
    }


}
