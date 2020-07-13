package org.pp.springwebfluxdemo.web.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.pp.springwebfluxdemo.web.model.ErrorResponse;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

public class RestAccessDeniedHandler implements ServerAccessDeniedHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, AccessDeniedException e) {
        return Mono.defer(() -> {
            return Mono.just(serverWebExchange.getResponse());
        }).flatMap((response) -> {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            ObjectMapper mapper = new ObjectMapper();
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            try {
                DataBuffer buffer = dataBufferFactory.wrap(mapper.writeValueAsBytes(ErrorResponse.builder().code(HttpStatus.FORBIDDEN.value()).message("Access Denied").build()));
                return response.writeWith(Mono.just(buffer)).doOnError((error) -> {
                    DataBufferUtils.release(buffer);
                });
            } catch (Exception ex) {
                DataBuffer buffer = dataBufferFactory.wrap("Access Denied".getBytes(Charset.defaultCharset()));
                return response.writeWith(Mono.just(buffer)).doOnError((error) -> {
                    DataBufferUtils.release(buffer);
                });
            }
        });
    }
}