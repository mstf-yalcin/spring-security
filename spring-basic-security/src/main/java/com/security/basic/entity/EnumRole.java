package com.security.basic.entity;

public enum EnumRole {

    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String role;

    EnumRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}



//package com.security.basic.entity;
//
//public enum EnumRole {
//
//    ROLE_USER("USER"),
//    ROLE_ADMIN("ADMIN");
//
//    private final String role;
//
//    EnumRole(String role) {
//        this.role = role;
//    }
//
//    public String getRole() {
//        return role;
//    }
//}
