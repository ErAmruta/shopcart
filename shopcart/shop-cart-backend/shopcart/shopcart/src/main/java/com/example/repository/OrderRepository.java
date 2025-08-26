package com.example.repository;


import com.example.model.Order;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
  List<Order> findByUser(User user);
}
