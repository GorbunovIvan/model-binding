package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.getAll();
    }

    public User getById(Integer id) {
        return userRepository.getById(id);
    }

    public List<User> getByIds(Set<Integer> ids) {
        return userRepository.getByIds(ids);
    }

    public User create(User user) {
        return userRepository.create(user);
    }

    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }
}
