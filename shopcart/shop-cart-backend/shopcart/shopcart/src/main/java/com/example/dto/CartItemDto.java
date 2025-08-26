package com.example.dto;
import lombok.*;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CartItemDto {
  private Long productId;
  private Integer qty;
}
