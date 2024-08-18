package org.example.repository;

import org.example.model.Product;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ProductRepository {

    private final List<Product> products = new ArrayList<>();

    public List<Product> getAll() {
        return new ArrayList<>(products);
    }

    public Product getById(Long id) {
        return products.stream()
                .filter(product -> Objects.equals(id, product.getId()))
                .findAny()
                .orElse(null);
    }

    public List<Product> getByIds(Set<Long> ids) {
        return products.stream()
                .filter(product -> ids.contains(product.getId()))
                .toList();
    }

    public synchronized Product create(Product product) {
        var id = nextId();
        product.setId(id);
        products.add(product);
        return product;
    }

    public synchronized void deleteById(Long id) {
        var product = this.getById(id);
        if (product != null) {
            products.remove(product);
        }
    }

    private Long nextId() {
        return products.stream()
                .map(Product::getId)
                .max(Comparator.naturalOrder())
                .orElse(0L) + 1;
    }
}
