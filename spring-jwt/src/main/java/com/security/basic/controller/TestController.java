package com.security.basic.controller;


import com.security.basic.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
    private final UserRepository userRepository;

    public TestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String test(Authentication authentication) {
        return authentication.getPrincipal().toString();
    }

    @GetMapping("/{username}")
    public String getUserAuth(@PathVariable String username, Authentication authentication) {

        if (username.equals(authentication.getName()))
            return userRepository.findByUsername(username).toString();
        else
            return "Invalid token";
    }
}
