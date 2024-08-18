package org.example.service.modelsBinding;

import org.example.model.PersistedModel;
import org.example.model.Product;
import org.example.model.User;
import org.example.repository.ProductRepository;
import org.example.repository.UserRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ModelsSearcherTest {

    @Autowired
    private ModelsSearcher modelsSearcher;

    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private UserRepository userRepository;

    private final EasyRandom easyRandom = new EasyRandom();

    // Miscellaneous cases
    @Test
    void shouldThrowExceptionWhenFindObjectByReference() {

        var model = new PersistedModel<Integer>() {
            @Override
            public Integer getUniqueIdentifierForBindingWithOtherServices() {
                return 0;
            }
        };

        assertThrows(RuntimeException.class, () -> modelsSearcher.findObjectByReference(model));
    }

    @Test
    void shouldReturnEmptyCollectionWhenFindObjectsByReferences() {
        var models = modelsSearcher.findObjectsByReferences(Collections.emptyList());
        assertNotNull(models);
        assertTrue(models.isEmpty());
    }

    @Test
    void shouldThrowExceptionWhenFindObjectsByReferences() {

        var model = new PersistedModel<Integer>() {
            @Override
            public Integer getUniqueIdentifierForBindingWithOtherServices() {
                return 0;
            }
        };

        var models = new ArrayList<PersistedModel<?>>();
        models.add(model);

        assertThrows(RuntimeException.class, () -> modelsSearcher.findObjectsByReferences(models));
    }

    // Users
    @Test
    void shouldReturnUserWhenFindObjectByReference() {

        var userExisting = easyRandom.nextObject(User.class);

        var user = easyRandom.nextObject(User.class);
        user.setId(userExisting.getId());

        var userUniqueIdentifier = user.getUniqueIdentifierForBindingWithOtherServices();

        when(userRepository.getById(userUniqueIdentifier)).thenReturn(userExisting);

        var userFound = modelsSearcher.findObjectByReference(user);
        assertNotNull(userFound);
        assertEquals(userExisting, userFound);

        verify(userRepository, times(1)).getById(userUniqueIdentifier);
    }

    @Test
    void shouldReturnUsersWhenFindObjectsByReferences() {

        var usersExisting = easyRandom.objects(User.class, 5).toList();

        var users = new ArrayList<User>();
        for (var userExisting : usersExisting) {
            var user = easyRandom.nextObject(User.class);
            user.setId(userExisting.getId());
            users.add(user);
        }

        var usersUniqueIdentifiers = PersistedModel.getUniqueIdentifiersOfCollectionOfModels(users, Integer.class);

        when(userRepository.getByIds(usersUniqueIdentifiers)).thenReturn(usersExisting);

        var usersFound = modelsSearcher.findObjectsByReferences(users);
        assertNotNull(usersFound);
        assertFalse(usersFound.isEmpty());
        assertEquals(usersExisting, usersFound);

        verify(userRepository, times(1)).getByIds(usersUniqueIdentifiers);
    }

    // Products
    @Test
    void shouldReturnProductWhenFindObjectByReference() {

        var productExisting = easyRandom.nextObject(Product.class);

        var product = easyRandom.nextObject(Product.class);
        product.setId(productExisting.getId());

        var productUniqueIdentifier = product.getUniqueIdentifierForBindingWithOtherServices();

        when(productRepository.getById(productUniqueIdentifier)).thenReturn(productExisting);

        var productFound = modelsSearcher.findObjectByReference(product);
        assertNotNull(productFound);
        assertEquals(productExisting, productFound);

        verify(productRepository, times(1)).getById(productUniqueIdentifier);
    }

    @Test
    void shouldReturnProductsWhenFindObjectsByReferences() {

        var productsExisting = easyRandom.objects(Product.class, 5).toList();

        var products = new ArrayList<Product>();
        for (var productExisting : productsExisting) {
            var product = easyRandom.nextObject(Product.class);
            product.setId(productExisting.getId());
            products.add(product);
        }

        var productsUniqueIdentifiers = PersistedModel.getUniqueIdentifiersOfCollectionOfModels(products, Long.class);

        when(productRepository.getByIds(productsUniqueIdentifiers)).thenReturn(productsExisting);

        var productsFound = modelsSearcher.findObjectsByReferences(products);
        assertNotNull(productsFound);
        assertFalse(productsFound.isEmpty());
        assertEquals(productsExisting, productsFound);

        verify(productRepository, times(1)).getByIds(productsUniqueIdentifiers);
    }
}