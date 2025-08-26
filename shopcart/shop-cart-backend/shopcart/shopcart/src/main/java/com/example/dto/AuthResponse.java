package com.example.dto;

import com.example.model.User;
import lombok.*;
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class AuthResponse {
  private String token;
  private User user;
}
