package com.security.basic.controller;


import com.security.basic.dto.request.AuthRequest;
import com.security.basic.dto.request.RegisterRequest;
import com.security.basic.entity.EnumRole;
import com.security.basic.entity.Role;
import com.security.basic.entity.User;
import com.security.basic.repository.RoleRepository;
import com.security.basic.repository.UserRepository;
import com.security.basic.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AuthController(JwtService jwtService, AuthenticationManager authenticationManager,
                          PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest authRequest) {

        User user = new User();
        user.setUsername(authRequest.username());
        user.setPassword(passwordEncoder.encode(authRequest.password()));

        Role userRole = roleRepository.findByAuthority(EnumRole.USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        user.setRoles(List.of(userRole));
        userRepository.save(user);

        return jwtService.generateToken(authRequest.username());
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest) {
        Authentication auth =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));

        if (auth.isAuthenticated()) {
            return jwtService.generateToken(authRequest.username());
        }

        throw new UsernameNotFoundException("Invalid username or password");
    }
}



