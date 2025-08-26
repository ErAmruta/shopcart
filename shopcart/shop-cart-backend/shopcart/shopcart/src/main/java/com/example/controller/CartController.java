package com.example.controller;

import com.example.model.CartItem;
import com.example.security.JwtUtil;
import com.example.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "${app.cors.allowedOrigin}")
public class CartController {

  private final CartService cartService;
  private final JwtUtil jwtUtil;

  public CartController(CartService cartService, JwtUtil jwtUtil) {
    this.cartService = cartService;
    this.jwtUtil = jwtUtil;
  }

  private String currentUserEmail(HttpServletRequest req) {
    String header = req.getHeader("Authorization");
    if (header != null && header.startsWith("Bearer ")) {
      String token = header.substring(7);
      if (jwtUtil.validateToken(token)) return jwtUtil.getEmailFromToken(token); // ✅ Use this
    }
    throw new RuntimeException("Unauthorized");
  }

  @GetMapping("/cart")
  public List<CartItem> getCart(HttpServletRequest req) {
    String email = currentUserEmail(req);
    return cartService.getCartForUser(email);
  }

  @PostMapping("/addToCart/{productId}")
  public CartItem addToCart(@PathVariable Long productId, HttpServletRequest req,
                            @RequestParam(defaultValue = "1") int qty) {
    String email = currentUserEmail(req);
    return cartService.addToCart(email, productId, qty);
  }

  @DeleteMapping("/cart/{cartItemId}")
  public void remove(@PathVariable Long cartItemId, HttpServletRequest req) {
    String email = currentUserEmail(req);
    cartService.removeItem(email, cartItemId);
  }
}
