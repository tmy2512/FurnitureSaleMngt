package org.example.furnituresaleproject.service.AccountService;

import org.example.furnituresaleproject.entity.Account;
import org.example.furnituresaleproject.form.account.CreateAccountForUserForm;
import org.example.furnituresaleproject.form.account.UpdateAccountForUserForm;
import org.example.furnituresaleproject.repository.IAccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private IAccountRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void createAccountForUser(CreateAccountForUserForm form) throws ParseException {
//        Account account = modelMapper.map(form, Account.class);
          Account account = new Account();
//        account.setRole(Role.CUSTOMER);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date birthday = dateFormat.parse(form.getBirthday());
        account.setName(form.getName());
        account.setEmail(form.getEmail());
        account.setAddress(form.getAddress());
        account.setBirthday(birthday);
        account.setPhone(form.getPhone());
        account.setPassword(form.getPassword());

        repository.save(account);
    }

// update account
    @Override
    public void updateAccountForUser(UpdateAccountForUserForm form) throws ParseException {
        Optional<Account> accountOptional = repository.findById(form.getId());
        Account account = accountOptional.get();
        account.setName(form.getName());
        account.setGender(Account.Gender.valueOf(form.getGender()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date birthday = dateFormat.parse(form.getBirthday());
        account.setBirthday(birthday);
        account.setPhone(form.getPhone());
        account.setAddress(form.getAddress());
        repository.save(account);
    }

    @Override
    public List<Account> getAllAccount() {
        return repository.findAll();
    }

    // get account infor to check authen at config file
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account  account = repository.findByEmail(email);
        if (account == null) {
            throw new UsernameNotFoundException(email);
        }

        return new org.springframework.security.core.userdetails.User
                (account.getEmail(),
                        account.getPassword(),
                        AuthorityUtils.createAuthorityList(String.valueOf(account.getRole())));
    }
}
