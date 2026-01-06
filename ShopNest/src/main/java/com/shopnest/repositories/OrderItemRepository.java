package com.shopnest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shopnest.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
