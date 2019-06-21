package com.example.services.impl;

import com.example.data.entities.UserEntity;
import com.example.repositories.UserRepository;
import com.example.services.UserService;
import com.example.shared.dto.UserDTO;
import com.example.shared.util.Util;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        userDTO.setUserId(Util.generateUID());
        userDTO.setEncryptedPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
        userEntity = userRepository.save(userEntity);
        UserDTO returnUserDTO = modelMapper.map(userEntity, UserDTO.class);
        return returnUserDTO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);
        if(userEntity == null) throw new UsernameNotFoundException(username);
        return new User(
                userEntity.getEmail(),
                userEntity.getEncryptedPassword(),
                true,
                true,
                true,
                true,
                new ArrayList<>());
    }

    @Override
    public UserDTO getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if(userEntity == null) throw new UsernameNotFoundException(email);
        return new ModelMapper().map(userEntity, UserDTO.class);
    }
}
