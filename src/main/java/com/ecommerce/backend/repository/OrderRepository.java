package com.ecommerce.backend.repository;

import com.ecommerce.backend.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    // Custom query method for fetching orders of a specific user
    List<Order> findByUserId(String userId);
}
