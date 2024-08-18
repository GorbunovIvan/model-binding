package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Product;
import org.example.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAll() {
        return productRepository.getAll();
    }

    public Product getById(Long id) {
        return productRepository.getById(id);
    }

    public List<Product> getByIds(Set<Long> ids) {
        return productRepository.getByIds(ids);
    }

    public Product create(Product product) {
        return productRepository.create(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
