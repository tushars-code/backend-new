package com.ecommerce.backend.controller;

import com.ecommerce.backend.model.CartItem;
import com.ecommerce.backend.model.Product;
import com.ecommerce.backend.repository.CartRepository;
import com.ecommerce.backend.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartRepository cartRepo;
    private final ProductRepository productRepo;

    public CartController(CartRepository cartRepo, ProductRepository productRepo) {
        this.cartRepo = cartRepo;
        this.productRepo = productRepo;
    }

    // ✅ Fetch all cart items
    @GetMapping
    public List<CartItemResponse> all() {
        List<CartItem> items = cartRepo.findAll();
        return items.stream().map(ci -> {
            Product p = productRepo.findById(ci.getProductId())
                    .orElse(new Product("Unknown", "No description", 0.0, "no-image.png")); // ✅ Fixed constructor call
            return new CartItemResponse(ci.getId(), p, ci.getQuantity());
        }).collect(Collectors.toList());
    }

    // ✅ Add item to cart
    @PostMapping
    public CartItem add(@RequestBody CartItem ci) {
        return cartRepo.save(ci);
    }

    // ✅ Remove item from cart
    @DeleteMapping("/{id}")
    public void remove(@PathVariable String id) {
        cartRepo.deleteById(id);
    }

    // DTO for response
    static class CartItemResponse {
        public String id;
        public Product product;
        public int quantity;

        public CartItemResponse(String id, Product product, int quantity) {
            this.id = id;
            this.product = product;
            this.quantity = quantity;
        }
    }
}
