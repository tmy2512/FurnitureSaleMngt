package org.example.furnituresaleproject.repository;

import org.example.furnituresaleproject.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepository extends JpaRepository<Order, Integer> {
}
