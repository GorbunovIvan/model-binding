package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.modelsBinding.ModelBinder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelBinder modelBinder;

    public List<User> getAll() {
        var result = userRepository.getAll();
        return modelBinder.bindFields(result);
    }

    public User getById(Integer id) {
        var result = userRepository.getById(id);
        return modelBinder.bindFields(result);
    }

    public List<User> getByIds(Set<Integer> ids) {
        var result = userRepository.getByIds(ids);
        return modelBinder.bindFields(result);
    }

    public User create(User user) {
        var result = userRepository.create(user);
        return modelBinder.bindFields(result);
    }

    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }
}
