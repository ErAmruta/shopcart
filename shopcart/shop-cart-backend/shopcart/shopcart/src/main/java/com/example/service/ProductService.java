package com.example.service;

import com.example.model.Product;
import com.example.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
  private final ProductRepository repo;
  public ProductService(ProductRepository repo) { this.repo = repo; }
  public List<Product> listAll() { return repo.findAll(); }
  public Product save(Product p) { return repo.save(p); }
}
