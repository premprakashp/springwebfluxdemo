package org.pp.springwebfluxdemo.web.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.pp.springwebfluxdemo.web.model.AppUser;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class ServerBodyCustomAuthnConverter implements ServerAuthenticationConverter {

    private static final String CUSTOM_AUTH_TOKEN = "authnToken";

    private ServerCodecConfigurer serverCodecConfigurer;
    private ReactiveRedisTemplate<String, String> reactiveRedisTemplateString;

    public ServerBodyCustomAuthnConverter(ServerCodecConfigurer serverCodecConfigurer, ReactiveRedisTemplate<String, String> reactiveRedisTemplateString) {
        this.serverCodecConfigurer = serverCodecConfigurer;
        this.reactiveRedisTemplateString = reactiveRedisTemplateString;
    }

    @SneakyThrows
    public Mono<Authentication> authenticate(ServerWebExchange serverWebExchange){
        ServerHttpRequest request = serverWebExchange.getRequest();
        String token = request.getHeaders().getFirst(CUSTOM_AUTH_TOKEN);
        if(StringUtils.isBlank(token)){
            return Mono.empty();
        }
        ObjectMapper objectMapper = new ObjectMapper();

        if ("admintoken".equals(token)) {
            AppUser user = AppUser.builder().name("admin").department("dept1").build();
            List<String> roles = Stream.of("ADMIN", "USER").collect(Collectors.toList());
            AppAuthnProfile authnProfile = AppAuthnProfile.builder().user(user).roles(roles).build();
            return reactiveRedisTemplateString.opsForValue().set(UUID.randomUUID().toString(), objectMapper.writeValueAsString(authnProfile))
                    .map(b -> new CustomAuthn(new CustomAuthnToken(user.getName(), token), authnProfile, true));
        } else if ("usertoken".equals(token)) {
            AppUser user = AppUser.builder().name("user").department("dept2").build();
            List<String> roles = Stream.of("USER").collect(Collectors.toList());
            AppAuthnProfile authnProfile = AppAuthnProfile.builder().user(user).roles(roles).build();
            return reactiveRedisTemplateString.opsForValue().set(UUID.randomUUID().toString(), objectMapper.writeValueAsString(authnProfile))
                    .map(b -> new CustomAuthn(new CustomAuthnToken(user.getName(), token), authnProfile, true));
        }

        return Mono.empty();

    }

    @Override
    public Mono<Authentication> convert(ServerWebExchange serverWebExchange) {
        log.info("ServerBodyCustomAuthnConverter apply called");
        return authenticate(serverWebExchange);
    }
}
