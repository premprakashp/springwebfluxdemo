package org.pp.springwebfluxdemo.web.security;

import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class CustomAuthn implements Authentication {

    private AppAuthnProfile authnProfile;
    private CustomAuthnToken customAuthnToken;
    private boolean authenticated;

    public CustomAuthn(CustomAuthnToken customAuthnToken, AppAuthnProfile authnProfile, boolean authenticated){
        this.authnProfile = authnProfile;
        this.customAuthnToken = customAuthnToken;
        this.authenticated = authenticated;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Flux.fromIterable(authnProfile.getRoles())
                .map(role -> new SimpleGrantedAuthority("ROLE_"+role))
                .collectList()
                .block();
    }

    @Override
    public Object getCredentials() {
        return Objects.nonNull(customAuthnToken)?customAuthnToken.getCredentials(): Strings.EMPTY;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return Objects.nonNull(authnProfile.getUser())?authnProfile.getUser().getName():null;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Invalid operation");
    }

    @Override
    public String getName() {
        return Objects.nonNull(authnProfile.getUser())?authnProfile.getUser().getName():null;
    }
}
