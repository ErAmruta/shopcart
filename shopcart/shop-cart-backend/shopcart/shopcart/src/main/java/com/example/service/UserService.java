package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository repo;
  private final PasswordEncoder encoder;

  public UserService(UserRepository repo, PasswordEncoder encoder) {
    this.repo = repo;
    this.encoder = encoder;
  }

  public User register(User user) {
    if (repo.existsByEmail(user.getEmail())) {
      throw new IllegalArgumentException("Email already in use");
    }
    user.setPassword(encoder.encode(user.getPassword()));
    return repo.save(user);
  }

  public User findByEmail(String email) {
    return repo.findByEmail(email).orElse(null);
  }

  public boolean checkPassword(User user, String rawPassword) {
    return encoder.matches(rawPassword, user.getPassword());
  }
}
