package com.example.services;

import com.example.shared.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDTO createUser(UserDTO userDTO);
    UserDTO getUserDetailsByEmail(String email);
    UserDTO getUserDetailsByUserId(String userId);

}
