package com.dmts.budget.dto;

public class UserDto {

    private String username;
    private String email;
    private String roleName;

    public UserDto(String username, String email, String roleName)
    {
        this.username = username;
        this.email = email;
        this.roleName = roleName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
