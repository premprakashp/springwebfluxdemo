package org.pp.springwebfluxdemo.web.config;

import org.pp.springwebfluxdemo.web.SpringwebfluxdemoApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringwebfluxdemoApplication.class);
    }

}
