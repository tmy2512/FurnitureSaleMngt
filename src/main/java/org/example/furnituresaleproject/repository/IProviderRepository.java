package org.example.furnituresaleproject.repository;

import org.example.furnituresaleproject.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProviderRepository extends JpaRepository<Provider, Integer> {
}
