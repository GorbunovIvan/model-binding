package org.example.service;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.modelsBinding.ModelBinder;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private ModelBinder modelBinder;

    private final EasyRandom easyRandom = new EasyRandom();

    @BeforeEach
    void setUp() {
        when(modelBinder.bindFields(any())).thenAnswer(ans -> ans.getArgument(0));
    }

    @Test
    void shouldReturnListOfUsersWhenGetAll() {

        var usersExpected = easyRandom.objects(User.class, 3).toList();

        when(userRepository.getAll()).thenReturn(usersExpected);

        var users = userService.getAll();
        assertNotNull(users);
        assertEquals(usersExpected, users);

        verify(userRepository, times(1)).getAll();
        verify(modelBinder, times(1)).bindFields(usersExpected);
    }

    // ...
}