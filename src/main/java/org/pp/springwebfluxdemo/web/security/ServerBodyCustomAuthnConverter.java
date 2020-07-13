package org.pp.springwebfluxdemo.web.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Slf4j
public class ServerBodyCustomAuthnConverter implements ServerAuthenticationConverter {

    private static final String CUSTOM_AUTH_TOKEN = "authnToken";

    private ServerCodecConfigurer serverCodecConfigurer;

    public ServerBodyCustomAuthnConverter(ServerCodecConfigurer serverCodecConfigurer) {
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    @Override
    public Mono<Authentication> convert(ServerWebExchange serverWebExchange) {
        log.info("ServerBodyCustomAuthnConverter apply called");
        return Mono.just(serverWebExchange.getRequest().getHeaders())
                .handle((httpHeaders, synchronousSink) -> {
                    String token = httpHeaders.getFirst(CUSTOM_AUTH_TOKEN);
                    if(StringUtils.isNotBlank(token)){
                        synchronousSink.next(token);
                    }
                })
                .map(token -> new CustomAuthnToken(null, token));
    }
}
