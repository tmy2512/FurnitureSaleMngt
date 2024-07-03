package org.example.furnituresaleproject.repository;

import org.example.furnituresaleproject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    List<Product> findByIdIn(List<Integer> ids);
}
