package org.example.furnituresaleproject.service.AccountService;

import org.example.furnituresaleproject.config.jwt.JwtTokenUtil;
import org.example.furnituresaleproject.entity.Account;
import org.example.furnituresaleproject.form.account.CreateAccountForUserForm;
import org.example.furnituresaleproject.form.account.UpdateAccountForUserForm;
import org.example.furnituresaleproject.repository.IAccountRepository;
import org.example.furnituresaleproject.request.AuthRequest;
import org.example.furnituresaleproject.response.AuthResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class AccountService implements IAccountService{

    @Autowired
    private IAccountRepository repository;

    @Autowired
    private UserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ModelMapper modelMapper;

    public AccountService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

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

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Account account = repository.findByEmail(authRequest.getEmail());
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
        String token = jwtTokenUtil.generateToken(userDetails);
        return new AuthResponse(token, account);
    }

    @Override
    public Integer getUserIdByEmail(String email) {
        Account account = repository.findByEmail(email);
        return account.getId();
    }

    // get account infor to check authen at config file tra ve userDetail

}
