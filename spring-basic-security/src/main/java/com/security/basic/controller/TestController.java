package com.security.basic.controller;


import com.security.basic.service.TestService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping
    public String test(Authentication authentication) {
        return authentication.getPrincipal().toString()
                + " .... " + authentication.getAuthorities().toString()
                + "..... " + authentication.getDetails().toString()
                + "....." + authentication.getName();
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('USER','ADMIN') and #username == authentication.name")
    @RolesAllowed({"ADMIN", "USER"})
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public String get(@PathVariable String username) {
        return testService.test();
    }

    @GetMapping("/admin/{username}")
    @PostAuthorize("returnObject == @testController.data()")
    public String getAdmin(@PathVariable String username) {
        return "secured role Admin";
    }

    public String data() {
        return "secured role Admin";
    }

}
