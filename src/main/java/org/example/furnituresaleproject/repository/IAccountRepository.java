package org.example.furnituresaleproject.repository;

import org.example.furnituresaleproject.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IAccountRepository extends JpaRepository<Account, Integer>
{
    public Account findByEmail(String email);
}
