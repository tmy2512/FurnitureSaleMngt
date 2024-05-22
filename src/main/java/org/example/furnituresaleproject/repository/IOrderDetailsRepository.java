package org.example.furnituresaleproject.repository;

import org.example.furnituresaleproject.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {
}
