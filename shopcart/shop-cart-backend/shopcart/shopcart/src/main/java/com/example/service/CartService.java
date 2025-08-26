package com.example.service;

import com.example.model.CartItem;
import com.example.repository.CartItemRepository;
import com.example.repository.ProductRepository;
import com.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CartService {
  private final CartItemRepository cartRepo;
  private final ProductRepository productRepo;
  private final UserRepository userRepo;

  public CartService(CartItemRepository cartRepo, ProductRepository productRepo, UserRepository userRepo) {
    this.cartRepo = cartRepo;
    this.productRepo = productRepo;
    this.userRepo = userRepo;
  }

  public List<CartItem> getCartForUser(String email) {
    var user = userRepo.findByEmail(email).orElseThrow();
    return cartRepo.findByUser(user);
  }

  public CartItem addToCart(String email, Long productId, int qty) {
    var user = userRepo.findByEmail(email).orElseThrow();
    var product = productRepo.findById(productId).orElseThrow();
    var existing = cartRepo.findByUserAndProductId(user, productId);
    if (existing.isPresent()) {
      var it = existing.get();
      it.setQty(it.getQty() + qty);
      return cartRepo.save(it);
    } else {
      var it = new CartItem();
      it.setUser(user);
      it.setProduct(product);
      it.setQty(qty);
      return cartRepo.save(it);
    }
  }

  public void removeItem(String email, Long cartItemId) {
    var user = userRepo.findByEmail(email).orElseThrow();
    cartRepo.findById(cartItemId).ifPresent(ci -> {
      if (ci.getUser().getId().equals(user.getId())) cartRepo.delete(ci);
    });
  }

  public void clearCart(String email) {
    var user = userRepo.findByEmail(email).orElseThrow();
    cartRepo.deleteByUser(user);
  }
}
