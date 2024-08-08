package org.example.furnituresaleproject.service.AccountService;

import org.example.furnituresaleproject.entity.Account;
import org.example.furnituresaleproject.form.account.CreateAccountForUserForm;
import org.example.furnituresaleproject.form.account.UpdateAccountForUserForm;
import org.example.furnituresaleproject.request.AuthRequest;
import org.example.furnituresaleproject.response.AuthResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.text.ParseException;
import java.util.List;

public interface IAccountService {
    void createAccountForUser(CreateAccountForUserForm form) throws ParseException;
    void updateAccountForUser(UpdateAccountForUserForm form) throws ParseException;
    List<Account> getAllAccount();
    AuthResponse login(AuthRequest authRequest) ;
}
