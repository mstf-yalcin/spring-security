package com.security.basic;

import com.security.basic.entity.EnumRole;
import com.security.basic.entity.Role;
import com.security.basic.entity.User;
import com.security.basic.repository.RoleRepository;
import com.security.basic.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class JwtApplication implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public JwtApplication(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(JwtApplication.class, args);
    }

    @Override
    public void run(String... args) {
        AddUser();
    }

    public void AddUser() {

        Role r1 = new Role();
        r1.setAuthority(EnumRole.ADMIN);

        Role r2 = new Role();
        r2.setAuthority(EnumRole.USER);

        roleRepository.saveAll(List.of(r1, r2));

        User u1 = new User();
        u1.setUsername("test");
        u1.setPassword(passwordEncoder.encode("test"));
        u1.setRoles(List.of(r1));

        User u2 = new User();
        u2.setUsername("test2");
        u2.setPassword(passwordEncoder.encode("test"));
        u2.setRoles(List.of(r1, r2));


        userRepository.saveAll(List.of(u1, u2));
    }
}
