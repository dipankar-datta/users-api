package com.example.ui.model;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRequest {

    @NotNull(message = "First name cannot be null")
    @Size(min = 2, message = "First name should be minimum 2 characters")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    @Size(min = 2, message = "Last name should be minimum 2 characters")
    private String lastName;

    @NotNull(message = "Email cannot be null")
    @Email
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 4, max = 8, message = "Password should be minimum 4 and maximum 8 characters")
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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
