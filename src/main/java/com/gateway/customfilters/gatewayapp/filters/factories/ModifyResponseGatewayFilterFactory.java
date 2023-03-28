package com.gateway.customfilters.gatewayapp.filters.factories;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
@Slf4j
public class ModifyResponseGatewayFilterFactory extends AbstractGatewayFilterFactory<ModifyResponseGatewayFilterFactory.Config> {


    public ModifyResponseGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    ServerHttpResponse response = exchange.getResponse();
                    Optional.ofNullable(exchange.getRequest()
                        .getQueryParams()
                        .getFirst("locale"))
                        .ifPresent(qp -> {
                            response.getHeaders()
                                .add("Custom-Language-Header", "custom-language-header");
                            log.info("Added custom header to Response");
                        });
                }));
        };
    }

    public static class Config {
    }
}
