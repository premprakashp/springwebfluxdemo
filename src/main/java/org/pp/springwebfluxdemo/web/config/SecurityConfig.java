package org.pp.springwebfluxdemo.web.config;

import org.pp.springwebfluxdemo.web.security.CustomAuthnSecurityFilter;
import org.pp.springwebfluxdemo.web.security.CustomReactiveAuthnManager;
import org.pp.springwebfluxdemo.web.security.RestAccessDeniedHandler;
import org.pp.springwebfluxdemo.web.security.RestAuthnEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http,
            ServerCodecConfigurer serverCodecConfigurer) {
        return http.addFilterAt(new CustomAuthnSecurityFilter(authenticationManager(), serverCodecConfigurer),
                SecurityWebFiltersOrder.AUTHENTICATION)
                .authorizeExchange(exchanges ->
                exchanges
                        .pathMatchers(HttpMethod.POST, "/v1/app/user").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.GET, "/v1/app/users").hasAnyRole("ADMIN", "USER")
                        .pathMatchers("/**").denyAll()
                        .anyExchange().denyAll())
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .logout().disable()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authnEntryPoint())
                .and()
                .build();
    }

    @Bean
    public CustomReactiveAuthnManager authenticationManager() {
        CustomReactiveAuthnManager manager = new CustomReactiveAuthnManager();
        return manager;
    }

    @Bean
    public RestAccessDeniedHandler accessDeniedHandler(){
        return new RestAccessDeniedHandler();
    }

    @Bean
    public RestAuthnEntryPoint authnEntryPoint(){
        return new RestAuthnEntryPoint();
    }
}
