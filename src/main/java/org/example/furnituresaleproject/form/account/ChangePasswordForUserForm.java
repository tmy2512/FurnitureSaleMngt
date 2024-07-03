package org.example.furnituresaleproject.form.account;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangePasswordForUserForm {

    private String email;
    private String oldPassword;
    private String newPassword;

}
