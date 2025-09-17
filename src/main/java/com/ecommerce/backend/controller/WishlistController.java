package com.ecommerce.backend.controller;

import com.ecommerce.backend.model.Product;
import com.ecommerce.backend.model.WishlistItem;
import com.ecommerce.backend.repository.ProductRepository;
import com.ecommerce.backend.repository.WishlistRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {
  private final WishlistRepository repo;
  private final ProductRepository productRepo;
  public WishlistController(WishlistRepository repo, ProductRepository productRepo){this.repo=repo; this.productRepo=productRepo;}

  @GetMapping
  public List<Product> all(){
    return repo.findAll()
               .stream()
               .map(w -> productRepo.findById(w.getProductId()).orElse(null))
               .collect(Collectors.toList());
  }

  @PostMapping
  public WishlistItem add(@RequestBody WishlistItem item){ return repo.save(item); }

  @DeleteMapping("/{id}")
  public void remove(@PathVariable String id){ repo.deleteById(id); }
}
