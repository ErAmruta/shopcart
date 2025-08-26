package com.example.controller;

import com.example.security.JwtUtil;
import com.example.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "${app.cors.allowedOrigin}")
public class PaymentController {

  private final OrderService orderService;
  private final JwtUtil jwtUtil;

  public PaymentController(OrderService orderService, JwtUtil jwtUtil) {
    this.orderService = orderService;
    this.jwtUtil = jwtUtil;
  }

  private String currentUserEmail(HttpServletRequest req) {
    String header = req.getHeader("Authorization");
    if (header != null && header.startsWith("Bearer ")) {
      String token = header.substring(7);
      if (jwtUtil.validateToken(token)) {
        return jwtUtil.getEmailFromToken(token); // ✅ Correct method
      }
    }
    throw new RuntimeException("Unauthorized");
  }

  @PostMapping("/payment")
  public String pay(HttpServletRequest req, @RequestBody java.util.Map<String, Object> body) {
    String email = currentUserEmail(req); // optional if you want to track user
    // For demo: accept amount and orderId optionally
    Object orderIdObj = body.get("orderId");
    if (orderIdObj != null) {
      Long id = Long.parseLong(orderIdObj.toString());
      orderService.markPaid(id);
      return "PAID";
    }
    return "OK";
  }
}
