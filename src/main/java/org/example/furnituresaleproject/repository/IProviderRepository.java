package org.example.furnituresaleproject.repository;

import org.example.furnituresaleproject.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProviderRepository extends JpaRepository<Provider, Integer> {
}
