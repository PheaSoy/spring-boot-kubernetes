package org.soyphea.k8s.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@ConfigurationProperties(prefix = "user")
@Component
@Validated
@Data
public class UserConfig {

    @NotEmpty(message = "name must be not empty")
    String name;

    @NotEmpty(message = "blog must be not empty")
    String blog;
}
