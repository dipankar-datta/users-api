package com.example.repositories;

import com.example.data.entities.UserEntity;
import com.example.shared.dto.UserDTO;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
}
