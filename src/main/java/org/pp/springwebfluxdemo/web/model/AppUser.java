package org.pp.springwebfluxdemo.web.model;


import lombok.*;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AppUser {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String department;

}
