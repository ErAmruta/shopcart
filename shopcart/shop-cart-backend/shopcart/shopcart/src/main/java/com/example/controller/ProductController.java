package com.example.controller;

import com.example.model.Product;
import com.example.service.ProductService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "${app.cors.allowedOrigin}")
public class ProductController {
  private final ProductService productService;
  public ProductController(ProductService productService) { this.productService = productService; }

  @GetMapping
  public List<Product> all() { return productService.listAll(); }

  @PostMapping
  public Product add(@RequestBody Product p) { return productService.save(p); }
}
