package org.example.furnituresaleproject.form.account;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class UpdateAccountForUserForm {

    private int id;
    private String name;
    private String address;
    private String birthday;
    private String phone;
    private String gender;
}
