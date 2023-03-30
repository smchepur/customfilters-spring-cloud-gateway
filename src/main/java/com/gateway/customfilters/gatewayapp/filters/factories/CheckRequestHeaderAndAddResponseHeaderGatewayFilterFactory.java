package com.gateway.customfilters.gatewayapp.filters.factories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CheckRequestHeaderAndAddResponseHeaderGatewayFilterFactory extends AbstractGatewayFilterFactory<CheckRequestHeaderAndAddResponseHeaderGatewayFilterFactory.Config> {

    public CheckRequestHeaderAndAddResponseHeaderGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("defaultLocale");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String headerValue = exchange.getRequest().getHeaders().get("customHeader").get(0);
            if (!headerValue.isEmpty()) {
                exchange.getResponse().getHeaders().add("customHeaderFromRequest",headerValue);
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {
    }
}
