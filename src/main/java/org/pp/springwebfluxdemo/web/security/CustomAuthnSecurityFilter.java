package org.pp.springwebfluxdemo.web.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

@Slf4j
public class CustomAuthnSecurityFilter extends AuthenticationWebFilter {

    private ReactiveRedisTemplate<String, String> reactiveRedisTemplateString;
    
    private final ServerCodecConfigurer serverCodecConfigurer;
    
    public CustomAuthnSecurityFilter(ReactiveAuthenticationManager authenticationManager,
                                     ServerCodecConfigurer serverCodecConfigurer,
                                     ReactiveRedisTemplate<String, String> reactiveRedisTemplateString) {
        super(authenticationManager);
        log.info("Inside CustomAuthnSecurityFilter");
        this.serverCodecConfigurer = serverCodecConfigurer;
        this.reactiveRedisTemplateString = reactiveRedisTemplateString;

        setServerAuthenticationConverter(new ServerBodyCustomAuthnConverter(this.serverCodecConfigurer, this.reactiveRedisTemplateString));

       // setAuthenticationSuccessHandler(new StatusCodeAuthSuccessHandler());
    }
}
