package org.pp.springwebfluxdemo.web.exception;

import lombok.Getter;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.HashMap;
import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {
    @Getter
    private String message = "Bad request";

    public GlobalErrorAttributes() {
        super();
    }

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String,Object> originalMap = super.getErrorAttributes(request, options);
        Map<String, Object> map = new HashMap<>();
        map.put("code", originalMap.getOrDefault("status", HttpStatus.BAD_REQUEST.value()));
        map.put("message", originalMap.getOrDefault("error", getMessage()));
        return map;
    }
}