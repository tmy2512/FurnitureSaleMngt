package org.example.furnituresaleproject.repository;

import org.example.furnituresaleproject.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface IAccountRepository extends JpaRepository<Account, Integer>
{
    public Account findByEmail(String email);
    List<Account> findByNameIn(Set<String> dataCus);
}
