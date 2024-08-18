package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Product;
import org.example.repository.ProductRepository;
import org.example.service.modelsBinding.ModelBinder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelBinder modelBinder;

    public List<Product> getAll() {
        var result = productRepository.getAll();
        return modelBinder.bindFields(result);
    }

    public Product getById(Long id) {
        var result = productRepository.getById(id);
        return modelBinder.bindFields(result);
    }

    public List<Product> getByIds(Set<Long> ids) {
        var result = productRepository.getByIds(ids);
        return modelBinder.bindFields(result);
    }

    public Product create(Product product) {
        var result = productRepository.create(product);
        return modelBinder.bindFields(result);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
