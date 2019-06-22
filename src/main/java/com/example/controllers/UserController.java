package com.example.controllers;

import com.example.services.UserService;
import com.example.shared.dto.UserDTO;
import com.example.ui.model.UserRequest;
import com.example.ui.model.UserResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private Environment environment;

    @Autowired
    private UserService userService;

    @GetMapping("/status/check")
    public String status() {
        return "UserController working on port: " + environment.getProperty("local.server.port")
                + ", with token "+ environment.getProperty("token.secret");
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDTO userDTO = modelMapper.map(userRequest, UserDTO.class);
        userDTO = userService.createUser(userDTO);

        UserResponse userResponse = modelMapper.map(userDTO, UserResponse.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }
}
