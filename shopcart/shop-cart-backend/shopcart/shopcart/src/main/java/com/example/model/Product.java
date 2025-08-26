package com.example.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Product {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  @Column(length = 1000)
  private String description;
  private Double price;
  private Double discountPrice; // nullable
  private String imageUrl;
  private Double rating;
  private Integer reviewCount;
}
