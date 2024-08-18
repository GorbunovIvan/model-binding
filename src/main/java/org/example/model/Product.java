package org.example.model;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@EqualsAndHashCode(of = { "name", "user", "createdAt" })
@ToString
public class Product {
    private Long id;
    private String name;
    private User user;
    private LocalDateTime createdAt;
}
