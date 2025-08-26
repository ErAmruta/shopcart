package com.example.controller;

import com.example.model.Order;
import com.example.security.JwtUtil;
import com.example.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "${app.cors.allowedOrigin}")
public class OrderController {

  private final OrderService orderService;
  private final JwtUtil jwtUtil;

  public OrderController(OrderService orderService, JwtUtil jwtUtil) {
    this.orderService = orderService;
    this.jwtUtil = jwtUtil;
  }

  // Extract current user email from JWT token
  private String currentUserEmail(HttpServletRequest req) {
    String header = req.getHeader("Authorization");
    if (header != null && header.startsWith("Bearer ")) {
      String token = header.substring(7);
      if (jwtUtil.validateToken(token)) {
        return jwtUtil.getEmailFromToken(token); // ✅ Use getEmailFromToken
      }
    }
    throw new RuntimeException("Unauthorized");
  }

  @PostMapping("/checkout")
  public Order checkout(HttpServletRequest req) {
    String email = currentUserEmail(req);
    return orderService.createOrderFromCart(email);
  }

  @GetMapping("/orders")
  public List<Order> myOrders(HttpServletRequest req) {
    String email = currentUserEmail(req);
    return orderService.listOrders(email);
  }

  @GetMapping("/order/{id}")
  public Order getOrder(@PathVariable Long id, HttpServletRequest req) {
    String email = currentUserEmail(req);
    Order order = orderService.getOrder(id);

    // Authorization check
    if (!order.getUser().getEmail().equals(email)) {
      throw new RuntimeException("Unauthorized access to this order");
    }

    return order;
  }

  @PostMapping("/order/{id}/pay")
  public Order pay(@PathVariable Long id, HttpServletRequest req) {
    String email = currentUserEmail(req);
    Order order = orderService.getOrder(id);

    // Authorization check
    if (!order.getUser().getEmail().equals(email)) {
      throw new RuntimeException("Unauthorized payment attempt");
    }

    return orderService.markPaid(id);
  }
}
