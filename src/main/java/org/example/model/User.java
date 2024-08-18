package org.example.model;

import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@EqualsAndHashCode(of = { "username" })
@ToString
public class User {
    private Integer id;
    private String username;
}
