package com.gateway.customfilters.gatewayapp.config;

import com.gateway.customfilters.gatewayapp.filters.factories.LoggingGatewayFilterFactory;
import com.gateway.customfilters.gatewayapp.filters.factories.ModifyRequestGatewayFilterFactory;
import com.gateway.customfilters.gatewayapp.filters.factories.ModifyResponseGatewayFilterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class CustomFiltersConfig {

    @Bean
    public LoggingGatewayFilterFactory loggingGatewayFilterFactory() {
        return new LoggingGatewayFilterFactory();
    }

    @Bean
    public ModifyRequestGatewayFilterFactory modifyRequestGatewayFilterFactory() {
        return new ModifyRequestGatewayFilterFactory();
    }

//    @Bean
//    public ModifyResponseGatewayFilterFactory modifyResponseGatewayFilterFactory() {
//        return new ModifyResponseGatewayFilterFactory();
//    }
}
