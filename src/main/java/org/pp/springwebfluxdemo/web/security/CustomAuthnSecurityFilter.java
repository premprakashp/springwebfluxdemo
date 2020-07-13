package org.pp.springwebfluxdemo.web.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

@Slf4j
public class CustomAuthnSecurityFilter extends AuthenticationWebFilter {
    
    private final ServerCodecConfigurer serverCodecConfigurer;
    
    public CustomAuthnSecurityFilter(ReactiveAuthenticationManager authenticationManager, ServerCodecConfigurer serverCodecConfigurer) {
        super(authenticationManager);
        log.info("Inside CustomAuthnSecurityFilter");
        this.serverCodecConfigurer = serverCodecConfigurer;

        setServerAuthenticationConverter(new ServerBodyCustomAuthnConverter(this.serverCodecConfigurer));

       // setAuthenticationSuccessHandler(new StatusCodeAuthSuccessHandler());
    }
}
