package com.shopnest.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopnest.entities.Order;
import com.shopnest.entities.User;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);
}
