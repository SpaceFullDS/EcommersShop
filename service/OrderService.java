package com.gmail.src.ecommerce.service;

import com.gmail.src.ecommerce.domain.Order;
import com.gmail.src.ecommerce.domain.Perfume;
import com.gmail.src.ecommerce.domain.User;

import java.util.List;

public interface OrderService {

    List<Perfume> getOrder(String username);

    Long finalizeOrder();

    List<Order> getUserOrdersList(String username);

    List<Order> findAll();

    Order save(Order order);

    List<Order> findOrderByUser(User user);
}
