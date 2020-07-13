package org.pp.springwebfluxdemo.web.model;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    private String message;
}
