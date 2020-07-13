package org.pp.springwebfluxdemo.web.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.pp.springwebfluxdemo.web.model.AppUser;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class CustomReactiveAuthnManager implements ReactiveAuthenticationManager {
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        log.info("CustomReactiveAuthnManager authenticate called");
        if(authentication instanceof CustomAuthnToken){
            CustomAuthnToken customAuthnToken = (CustomAuthnToken) authentication;
            String token = (String)authentication.getCredentials();
            // dummy auth
            if(StringUtils.isNotBlank(token)) {
                if ("admintoken".equals(token)) {
                    AppUser user = AppUser.builder().name("admin").department("dept1").build();
                    List<String> roles = Stream.of("ADMIN", "USER").collect(Collectors.toList());
                    AppAuthnProfile authnProfile = AppAuthnProfile.builder().user(user).roles(roles).build();
                    return Mono.just(new CustomAuthn(customAuthnToken, authnProfile, true));
                } else if ("usertoken".equals(token)) {
                    AppUser user = AppUser.builder().name("user").department("dept2").build();
                    List<String> roles = Stream.of("USER").collect(Collectors.toList());
                    AppAuthnProfile authnProfile = AppAuthnProfile.builder().user(user).roles(roles).build();
                    return Mono.just(new CustomAuthn(customAuthnToken, authnProfile, true));
                }
            }
        }
        throw new BadCredentialsException("Invalid token"); // returns 401
        //return Mono.just(new CustomAuthn(null, null, false)); // returns 403
    }
}
