package com.example.ui.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserLoginRequest {

    @NotNull(message = "Email cannot be null")
    @Email
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 4, max = 8, message = "Password length should more than 4 and less than 9")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
