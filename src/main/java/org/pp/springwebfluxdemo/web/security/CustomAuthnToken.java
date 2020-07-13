package org.pp.springwebfluxdemo.web.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomAuthnToken extends UsernamePasswordAuthenticationToken {

    public CustomAuthnToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public CustomAuthnToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
