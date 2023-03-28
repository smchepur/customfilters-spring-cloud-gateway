package com.gateway.customfilters.gatewayapp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(exclude = {ReactiveSecurityAutoConfiguration.class })
public class CustomFiltersGatewayApplication {

    public static void main(String[] args) {

        new SpringApplicationBuilder(CustomFiltersGatewayApplication.class)
                .profiles("customfilters")
                .run(args);
    }

}