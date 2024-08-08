package org.example.furnituresaleproject.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.furnituresaleproject.entity.Account;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String accessToken;
    private String tokenType = "Bearer ";
    private int id;
    private String email;
    private String password;

    public AuthResponse(String accessToken, Account user) {
        this.accessToken = accessToken;
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }
}
