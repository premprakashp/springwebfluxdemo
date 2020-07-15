package org.pp.springwebfluxdemo.web.model;


import lombok.*;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AppUser {

    @Getter
    @Setter
    @NotBlank
    private String name;

    @Getter
    @Setter
    @NotBlank
    private String department;

}
