package org.example.furnituresaleproject.service.security;

import org.example.furnituresaleproject.entity.Account;
import org.example.furnituresaleproject.repository.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private IAccountRepository repository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = repository.findByEmail(email);
        if (account == null) {
            throw new UsernameNotFoundException(email);
        }

        return new org.springframework.security.core.userdetails.User
                (account.getEmail(),
                        account.getPassword(),
                        AuthorityUtils.createAuthorityList(String.valueOf(account.getRole())));

    }
}
