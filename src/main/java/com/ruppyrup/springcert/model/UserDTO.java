package com.ruppyrup.springcert.model;

public class UserDTO {
    private String username;
    private String password;

    @Override
    public String toString() {
        return "{" +
                "username='" + username + '\'' +
                ",\n password='" + password + '\'' +
                '}';
    }

    public UserDTO() {
    }

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDTO userDTO = (UserDTO) o;

        if (username != null ? !username.equals(userDTO.username) : userDTO.username != null) return false;
        return password != null ? password.equals(userDTO.password) : userDTO.password == null;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
