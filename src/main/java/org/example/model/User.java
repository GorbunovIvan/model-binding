package org.example.model;

import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@EqualsAndHashCode(of = { "username" })
@ToString
public class User implements PersistedModel<Integer> {

    private Integer id;
    private String username;

    @Override
    public Integer getUniqueIdentifierForBindingWithOtherServices() {
        return getId();
    }
}
