package com.security.basic.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public String test() {
        return "test";
    }

}
