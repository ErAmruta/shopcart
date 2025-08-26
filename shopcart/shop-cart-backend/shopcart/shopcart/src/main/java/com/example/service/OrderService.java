package com.example.service;

import com.example.model.Order;
import com.example.model.OrderItem;
import com.example.repository.CartItemRepository;
import com.example.repository.OrderRepository;
import com.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
  private final OrderRepository orderRepo;
  private final CartItemRepository cartRepo;
  private final UserRepository userRepo;

  public OrderService(OrderRepository orderRepo, CartItemRepository cartRepo, UserRepository userRepo) {
    this.orderRepo = orderRepo;
    this.cartRepo = cartRepo;
    this.userRepo = userRepo;
  }

  public Order createOrderFromCart(String email) {
    var user = userRepo.findByEmail(email).orElseThrow();
    var cartItems = cartRepo.findByUser(user);
    if (cartItems.isEmpty()) throw new IllegalStateException("Cart empty");

    var items = cartItems.stream().map(ci -> {
      var oi = new OrderItem();
      oi.setProductId(ci.getProduct().getId());
      oi.setName(ci.getProduct().getName());
      oi.setUnitPrice(ci.getProduct().getDiscountPrice() != null ? ci.getProduct().getDiscountPrice() : ci.getProduct().getPrice());
      oi.setQty(ci.getQty());
      return oi;
    }).collect(Collectors.toList());

    double total = items.stream().mapToDouble(i -> i.getUnitPrice() * i.getQty()).sum();

    var order = new Order();
    order.setUser(user);
    order.setItems(items);
    order.setTotal(total);
    order.setStatus("CREATED");
    var saved = orderRepo.save(order);

    // used to clear cart
    cartRepo.deleteByUser(user);

    return saved;
  }

  public List<Order> listOrders(String email) {
    var user = userRepo.findByEmail(email).orElseThrow();
    return orderRepo.findByUser(user);
  }

  public Order getOrder(Long id) {
    return orderRepo.findById(id).orElseThrow();
  }

  public Order markPaid(Long id) {
    var ord = orderRepo.findById(id).orElseThrow();
    ord.setStatus("PAID");
    return orderRepo.save(ord);
  }
}
