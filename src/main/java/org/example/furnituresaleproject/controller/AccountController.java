package org.example.furnituresaleproject.controller;

import org.example.furnituresaleproject.dto.AccountDTO;
import org.example.furnituresaleproject.entity.Account;
import org.example.furnituresaleproject.form.account.CreateAccountForUserForm;
import org.example.furnituresaleproject.form.account.UpdateAccountForUserForm;
import org.example.furnituresaleproject.service.AccountService.IAccountService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "api/v1/accounts")
public class AccountController {

    @Autowired
    private IAccountService service;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/customer")
    public void createAccountForUser(@RequestBody CreateAccountForUserForm form) throws ParseException {
        service.createAccountForUser(form);
    }

    @PutMapping("/customer")
    public void updateAccountForUser(@RequestBody UpdateAccountForUserForm form, @RequestParam int idU) throws ParseException {
        form.setId(idU);
        service.updateAccountForUser(form);
    }

    @GetMapping()
    public List<AccountDTO> getAllAccount() {
        List<Account> entityAccount = service.getAllAccount();
        return modelMapper.map(entityAccount, new TypeToken<List<AccountDTO>>() {}.getType());
    }
}
