package org.example.Domain;

public class User extends Entity<Long>{
    String username;
    String password;

    public User(Long id, String username, String password) {
        super.setId(id);
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
}
