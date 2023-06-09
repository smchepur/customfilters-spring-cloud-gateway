package com.gateway.customfilters.gatewayapp.filters.factories;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ModifyRequestGatewayFilterFactory extends AbstractGatewayFilterFactory<ModifyRequestGatewayFilterFactory.Config> {

    public ModifyRequestGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("defaultLocale");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (exchange.getRequest()
                .getHeaders()
                .getAcceptLanguage()
                .isEmpty()) {

                String queryParamLocale = exchange.getRequest()
                    .getQueryParams()
                    .getFirst("locale");

                Locale requestLocale = Optional.ofNullable(queryParamLocale)
                    .map(l -> Locale.forLanguageTag(l))
                    .orElse(config.getDefaultLocale());

                exchange.getRequest()
                    .mutate()
                    .headers(h -> h.setAcceptLanguageAsLocales(Collections.singletonList(requestLocale)));
            }

            String allOutgoingRequestLanguages = exchange.getRequest()
                .getHeaders()
                .getAcceptLanguage()
                .stream()
                .map(range -> range.getRange())
                .collect(Collectors.joining(","));

            log.info("Modify request output - Request contains Accept-Language header: {}", allOutgoingRequestLanguages);

            ServerWebExchange modifiedExchange = exchange.mutate()
                .request(originalRequest -> originalRequest.uri(UriComponentsBuilder.fromUri(exchange.getRequest()
                    .getURI())
                    .replaceQueryParams(new LinkedMultiValueMap<String, String>())
                    .build()
                    .toUri()))
                .build();

            ServerHttpRequest request = exchange.getRequest().mutate()
                    .headers(httpHeaders -> httpHeaders.add("customheader", "customValue")).build();

            //modifiedExchange.getRequest().getHeaders().add("customheader", "customValue");

            log.info("Removed all query params: {}", modifiedExchange.getRequest()
                .getURI());

            return chain.filter(exchange.mutate().request(request).build());
        };
    }

    public static class Config {
        private Locale defaultLocale;

        public Config() {
        }

        public Locale getDefaultLocale() {
            return defaultLocale;
        }

        public void setDefaultLocale(String defaultLocale) {
            this.defaultLocale = Locale.forLanguageTag(defaultLocale);
        };
    }
}
