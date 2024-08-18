package org.example.repository;

import org.example.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {

    private final List<User> users = new ArrayList<>();

    public List<User> getAll() {
        return new ArrayList<>(users);
    }

    public User getById(Integer id) {
        return users.stream()
                .filter(user -> Objects.equals(id, user.getId()))
                .findAny()
                .orElse(null);
    }

    public List<User> getByIds(Set<Integer> ids) {
        return users.stream()
                .filter(user -> ids.contains(user.getId()))
                .toList();
    }

    public synchronized User create(User user) {
        var id = nextId();
        user.setId(id);
        users.add(user);
        return user;
    }

    public synchronized void deleteById(Integer id) {
        var user = this.getById(id);
        if (user != null) {
            users.remove(user);
        }
    }

    private Integer nextId() {
        return users.stream()
                .map(User::getId)
                .max(Comparator.naturalOrder())
                .orElse(0) + 1;
    }
}
