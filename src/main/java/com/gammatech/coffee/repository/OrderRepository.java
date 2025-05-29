package com.gammatech.coffee.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gammatech.coffee.models.Order;
import com.gammatech.coffee.models.OrderStatus;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOrderStatus(OrderStatus status);
    List<Order> findAllByCustomerId(Long customerId);
}
