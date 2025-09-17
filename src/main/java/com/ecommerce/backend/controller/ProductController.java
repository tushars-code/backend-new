package com.ecommerce.backend.controller;

import com.ecommerce.backend.model.Product;
import com.ecommerce.backend.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
  private final ProductRepository repo;
  public ProductController(ProductRepository repo){ this.repo = repo; }

  @GetMapping
  public List<Product> all(){ return repo.findAll(); }

  @GetMapping("/{id}")
  public Product get(@PathVariable String id){ return repo.findById(id).orElse(null); }

  @GetMapping("/search")
  public List<Product> search(@RequestParam String q){ return repo.findByNameContainingIgnoreCase(q); }

  @PostMapping
  public Product create(@RequestBody Product p){ return repo.save(p); }

  @PutMapping("/{id}")
  public Product update(@PathVariable String id, @RequestBody Product p){
    p.setId(id);
    return repo.save(p);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable String id){ repo.deleteById(id); }
}
