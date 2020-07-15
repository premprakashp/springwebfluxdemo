package org.pp.springwebfluxdemo.web.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.server.DefaultServerRedirectStrategy;
import org.springframework.security.web.server.ServerRedirectStrategy;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
public class DocsRedirectFilter  implements WebFilter {

    private final ServerRedirectStrategy redirectStrategy = new DefaultServerRedirectStrategy();
    private ServerWebExchange exchange;

    private Mono<Void> redirectToDocs(){
        try {
            return this.redirectStrategy.sendRedirect(exchange,new URI("/docs/swagger-ui.html"));
        } catch (URISyntaxException e) {
            //chain.filter(exchange);
        }
        return Mono.empty();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if(path.matches("^/server/(docs/??)$")){
            log.info("docs url found redirecting, url->{}", path);
            try {
                return this.redirectStrategy.sendRedirect(exchange,new URI("/docs/swagger-ui.html"));
            } catch (URISyntaxException e) {
            }
        }
        log.debug("non docs url->{}, proceeding", path);
        return chain.filter(exchange);
    }
}
