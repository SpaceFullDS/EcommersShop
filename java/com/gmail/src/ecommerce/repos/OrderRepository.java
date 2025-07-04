package com.gmail.src.ecommerce.repos;

import com.gmail.src.ecommerce.domain.Order;
import com.gmail.src.ecommerce.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrderByUserId(Long userId);

    List<Order> findOrderByUser(User user);
}