package com.security.basic;

import com.security.basic.entity.EnumRole;
import com.security.basic.entity.Role;
import com.security.basic.entity.User;
import com.security.basic.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class SpringSecurityApplication implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SpringSecurityApplication(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
    }

    @Override
    public void run(String... args) {
        AddUser();
    }

    public void AddUser() {
        User u = new User();
        u.setUsername("test");
        u.setPassword(passwordEncoder.encode("test"));

        Role r = new Role();
        r.setAuthority(EnumRole.ADMIN);

        u.setRoles(List.of(r));
        userRepository.save(u);

    }
}
