package com.ecommerce.backend.repository;

import com.ecommerce.backend.model.WishlistItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WishlistRepository extends MongoRepository<WishlistItem, String> {
}
