package org.example.service;

import org.example.model.Product;
import org.example.repository.ProductRepository;
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
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private ModelBinder modelBinder;

    private final EasyRandom easyRandom = new EasyRandom();

    @BeforeEach
    void setUp() {
        when(modelBinder.bindFields(any())).thenAnswer(ans -> ans.getArgument(0));
    }

    @Test
    void shouldReturnProductWhenGetById() {

        var productExpected = easyRandom.nextObject(Product.class);
        var id = productExpected.getId();

        when(productRepository.getById(id)).thenReturn(productExpected);

        var product = productService.getById(id);
        assertNotNull(product);
        assertEquals(productExpected, product);

        verify(productRepository, times(1)).getById(id);
        verify(modelBinder, times(1)).bindFields(productExpected);
    }

    // ...
}