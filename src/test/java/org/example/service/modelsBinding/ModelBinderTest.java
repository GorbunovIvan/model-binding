package org.example.service.modelsBinding;

import org.example.model.Product;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashSet;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ModelBinderTest {

    @Autowired
    private ModelBinder modelBinder;

    @MockBean
    private UserRepository userRepository;

    private final EasyRandom easyRandom = new EasyRandom();

    // Miscellaneous cases
    @Test
    void shouldReturnNullWhenBindFields() {
        var result = modelBinder.bindFields(null);
        assertNull(result);
    }

    // Users
    @Test
    void shouldBindFieldsOfUserWhenBindFields() {

        var user = easyRandom.nextObject(User.class);
        var userExpected = copyUser(user);

        modelBinder.bindFields(user);
        assertEquals(userExpected, user);
    }

    @Test
    void shouldBindFieldsOfCollectionsOfUsersWhenBindFields() {

        var users = easyRandom.objects(User.class, 5).collect(Collectors.toSet());

        var usersExpected = new HashSet<User>();
        for (var user : users) {
            var userCopy = copyUser(user);
            usersExpected.add(userCopy);
        }

        modelBinder.bindFields(users);
        assertEquals(usersExpected, users);
    }

    // Products
    @Test
    void shouldBindFieldsOfProductWhenBindFields() {

        var userExisting = easyRandom.nextObject(User.class);

        var user = new User();
        user.setId(userExisting.getId());

        var product = easyRandom.nextObject(Product.class);
        product.setUser(user);

        var productExpected = copyProduct(product);
        productExpected.setUser(userExisting);

        when(userRepository.getById(user.getId())).thenReturn(userExisting);

        modelBinder.bindFields(product);
        assertEquals(productExpected, product);
        assertNotNull(product.getUser());
        assertNotNull(product.getUser().getUsername());
        assertEquals(userExisting, product.getUser());

        verify(userRepository, times(1)).getById(user.getId());
    }

    @Test
    void shouldBindFieldsOfCollectionOfProductsWhenBindFields() {

        var products = new HashSet<Product>();
        var productsExpected = new HashSet<Product>();

        var numberOfProducts = 6;

        var usersExisting = easyRandom.objects(User.class, numberOfProducts/2).toList();

        for (int i = 0; i < numberOfProducts; i++) {

            var userExisting = usersExisting.get(i/2);

            var user = new User();
            user.setId(userExisting.getId());

            var product = easyRandom.nextObject(Product.class);
            product.setUser(user);

            products.add(product);

            var productExpected = copyProduct(product);
            productExpected.setUser(userExisting);

            productsExpected.add(productExpected);
        }

        var usersIds = usersExisting.stream().map(User::getId).collect(Collectors.toSet());

        when(userRepository.getByIds(usersIds)).thenReturn(usersExisting);

        modelBinder.bindFields(products);

        assertEquals(productsExpected, products);

        for (var product : products) {
            assertNotNull(product.getUser());
            assertNotNull(product.getUser().getUsername());
        }

        verify(userRepository, times(1)).getByIds(usersIds);
        verify(userRepository, never()).getById(anyInt());
    }

    private User copyUser(User user) {
        return new User(user.getId(), user.getUsername());
    }

    private Product copyProduct(Product product) {
        return new Product(
                product.getId(),
                product.getName(),
                copyUser(product.getUser()),
                product.getCreatedAt());
    }
}