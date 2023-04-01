package edu.usersystem.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "edu.usersystem.integration")
public class FeignConfiguration {
}
