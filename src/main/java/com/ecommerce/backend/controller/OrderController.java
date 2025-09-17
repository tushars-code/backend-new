package com.ecommerce.backend.controller;

import com.ecommerce.backend.model.CartItem;
import com.ecommerce.backend.model.Order;
import com.ecommerce.backend.model.Product;
import com.ecommerce.backend.repository.CartRepository;
import com.ecommerce.backend.repository.OrderRepository;
import com.ecommerce.backend.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderRepository orderRepo;
    private final CartRepository cartRepo;
    private final ProductRepository productRepo;

    public OrderController(OrderRepository orderRepo, CartRepository cartRepo, ProductRepository productRepo) {
        this.orderRepo = orderRepo;
        this.cartRepo = cartRepo;
        this.productRepo = productRepo;
    }

    // ✅ Get all orders (for Admin Dashboard)
    @GetMapping
    public List<Order> allOrders() {
        return orderRepo.findAll();
    }

    // ✅ Get orders by customer (for tracking page)
    @GetMapping("/user/{userId}")
    public List<Order> userOrders(@PathVariable String userId) {
        return orderRepo.findByUserId(userId);
    }

    // ✅ Customer: Place new order
    @PostMapping
    public Order create(@RequestBody CreateOrderRequest req) {
        double total = 0;
        for (CartItem ci : req.items) {
            Product p = productRepo.findById(ci.getProductId()).orElse(null);
            if (p != null) {
                total += p.getPrice() * ci.getQuantity();
            }
        }

        Order o = new Order(req.userId, req.items, total);
        o.setStatus("PLACED"); // default status
        Order saved = orderRepo.save(o);

        // clear cart if requested
        if (req.clearCart) {
            cartRepo.deleteAll();
        }

        return saved;
    }

    // ✅ Admin: Update order status
    @PutMapping("/{id}/status")
    public Order updateStatus(@PathVariable String id, @RequestBody StatusUpdate su) {
        return orderRepo.findById(id).map(o -> {
            o.setStatus(su.status);
            return orderRepo.save(o);
        }).orElse(null);
    }

    // =============================
    // Request DTOs
    // =============================
    public static class CreateOrderRequest {
        public String userId;         // 🔹 track which customer placed the order
        public List<CartItem> items;
        public boolean clearCart = true;
    }

    public static class StatusUpdate {
        public String status;
    }
}
