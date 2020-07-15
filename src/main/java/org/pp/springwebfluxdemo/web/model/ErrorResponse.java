package org.pp.springwebfluxdemo.web.model;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    @Getter
    @Setter
    @NotBlank
    private int code;

    @Getter
    @Setter
    @NotBlank
    private String message;
}
