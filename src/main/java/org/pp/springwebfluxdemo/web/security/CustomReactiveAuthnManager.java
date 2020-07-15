package org.pp.springwebfluxdemo.web.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

@Slf4j
public class CustomReactiveAuthnManager implements ReactiveAuthenticationManager {
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        log.info("CustomReactiveAuthnManager authenticate called");
        if(authentication instanceof CustomAuthn){
            CustomAuthn customAuthn = (CustomAuthn) authentication;
            return Mono.just(authentication);
        }
        return Mono.defer(() -> Mono.just(new CustomAuthn(null, null, false))); // 403
    }
}
