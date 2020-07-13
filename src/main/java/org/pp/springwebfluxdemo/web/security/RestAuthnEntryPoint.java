package org.pp.springwebfluxdemo.web.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.pp.springwebfluxdemo.web.model.ErrorResponse;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

public class RestAuthnEntryPoint implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange serverWebExchange, AuthenticationException e) {
        return Mono.fromRunnable(() -> {
            ServerHttpResponse response = serverWebExchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            ObjectMapper mapper = new ObjectMapper();
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            try {
                DataBuffer buffer = dataBufferFactory.wrap(mapper.writeValueAsBytes(ErrorResponse.builder().code(HttpStatus.UNAUTHORIZED.value()).message("Unauthorized to access this resource").build()));
                response.writeWith(Mono.just(buffer)).doOnError((error) -> {
                    DataBufferUtils.release(buffer);
                }).block();
            } catch (Exception ex) {
                DataBuffer buffer = dataBufferFactory.wrap("Unauthorized to access this resource".getBytes(Charset.defaultCharset()));
                response.writeWith(Mono.just(buffer)).doOnError((error) -> {
                    DataBufferUtils.release(buffer);
                }).block();
            }
        });
    }
}
