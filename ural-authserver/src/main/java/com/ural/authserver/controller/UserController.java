package com.ural.authserver.controller;

import com.ural.authserver.controller.model.RegistrationRequest;
import com.ural.authserver.controller.model.RegistrationResponse;
import com.ural.authserver.entities.Role;
import com.ural.authserver.entities.UserEntity;
import com.ural.authserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //TODO IMPLEMENT BUSINESS LOGIC
    @PostMapping("api/v0/auth/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest request) {

        Set<Role> roles = Stream.of(Role.builder().id(1L).name("ROLE_USER").build()).collect(Collectors.toSet());

        UserEntity userEntity = new UserEntity(request.getFirstname(), request.getLastname()
                , request.getEmail(), passwordEncoder.encode(request.getPassword()), roles);

        userRepository.save(userEntity);

        return new ResponseEntity<>(new RegistrationResponse("test"), HttpStatus.CREATED);
    }


}
