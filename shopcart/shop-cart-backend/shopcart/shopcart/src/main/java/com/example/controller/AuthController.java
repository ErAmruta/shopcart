package com.example.controller;

import com.example.model.User;
import com.example.security.JwtUtil;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "${app.cors.allowedOrigin}")
public class AuthController {

  @Autowired
  private UserService userService;

  @Autowired
  private JwtUtil jwtUtil;

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@RequestBody User user) {
    var saved = userService.register(user);
    return ResponseEntity.ok(saved);
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
    var user = userService.findByEmail(email);
    if (user == null || !userService.checkPassword(user, password))
      return ResponseEntity.status(401).body("Invalid credentials");

    String token = jwtUtil.generateToken(user.getEmail());
    return ResponseEntity.ok(token);
  }
}
