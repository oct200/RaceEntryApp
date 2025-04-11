package org.example;


import org.model.User;

public interface UserRepositoryInterface extends RepoInterface<User,Long> {
    public User getUserByUsername(String username);
    public User getUserByUsernameAndPassword(String username, String password);
}
