package org.pp.springwebfluxdemo.web.security;

import lombok.*;
import org.pp.springwebfluxdemo.web.model.AppUser;

import java.util.List;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppAuthnProfile {
    @Getter
    @Setter
    private AppUser user;

    @Getter
    @Setter
    private List<String> roles;
}
