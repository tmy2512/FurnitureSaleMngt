package org.example.furnituresaleproject.repository;

import org.example.furnituresaleproject.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAccountRepository extends JpaRepository<Account, Integer> {
}
