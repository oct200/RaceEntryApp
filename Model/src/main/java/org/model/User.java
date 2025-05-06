package org.model;

public class User extends Entity<Long>{
    String username;
    String parola;

    public User(Long id, String username, String parola) {
        super.setId(id);
        this.username = username;
        this.parola = parola;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getParola() {
        return parola;
    }

    public void setparola(String parola) {
        this.parola = parola;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", parola='" + parola + '\'' +
                '}';
    }
}
