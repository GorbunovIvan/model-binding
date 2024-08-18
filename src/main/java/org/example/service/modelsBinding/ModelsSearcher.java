package org.example.service.modelsBinding;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.PersistedModel;
import org.example.model.Product;
import org.example.model.User;
import org.example.repository.ProductRepository;
import org.example.repository.UserRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Searching for models in other services by their unique identifiers
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ModelsSearcher {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public PersistedModel<?> findObjectByReference(@NonNull PersistedModel<?> model) {
        if (model instanceof User user) {
            return userRepository.getById(user.getUniqueIdentifierForBindingWithOtherServices());
        } else if (model instanceof Product product) {
            return productRepository.getById(product.getUniqueIdentifierForBindingWithOtherServices());
        } else {
            var errorMessage = String.format("Unable to bind entity of type '%s' - unknown model", model.getClass());
            log.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }
    }

    // If you use searching for a collections (f.e. "getByIds()" method)
    public Collection<? extends PersistedModel<?>> findObjectsByReferences(Collection<? extends PersistedModel<?>> models) {

        if (models.isEmpty()) {
            return models;
        }

        var firstModel = models.iterator().next();

        if (firstModel instanceof User) {
            var ids = PersistedModel.getUniqueIdentifiersOfCollectionOfModels(models, Integer.class);
            return userRepository.getByIds(ids);
        } else if (firstModel instanceof Product) {
            var ids = PersistedModel.getUniqueIdentifiersOfCollectionOfModels(models, Long.class);
            return productRepository.getByIds(ids);
        } else {
            var errorMessage = String.format("Unable to bind entities of type '%s' - unknown model", firstModel.getClass());
            log.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }
    }
}
