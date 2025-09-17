package com.ecommerce.backend.repository;

import com.ecommerce.backend.model.CartItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends MongoRepository<CartItem, String> {
    // You can add custom queries if needed, e.g.:
    // List<CartItem> findByUserId(String userId);
    // CartItem findByUserIdAndProductId(String userId, String productId);
}
