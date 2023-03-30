package com.gateway.customfilters.gatewayapp.config;

import com.gateway.customfilters.gatewayapp.filters.factories.LoggingGatewayFilterFactory;
import com.gateway.customfilters.gatewayapp.filters.factories.ModifyRequestGatewayFilterFactory;
import com.gateway.customfilters.gatewayapp.filters.factories.ModifyResponseGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class CustomFiltersConfig {

    /**
     * This KeyResolver is mandatory for RateLimit Filter*
     * to avoid 403 error being thrown.*
     * If this is not present, then set the property to false
     * denyEmptyKey: false to allow traffic to go through
     * * * * *
     * @return
     */
    @Bean
    KeyResolver userKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("locale"));
    }
}
