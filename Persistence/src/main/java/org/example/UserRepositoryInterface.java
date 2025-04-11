package org.example;


import org.model.User;

public interface UserRepositoryInterface extends RepoInterface<User,Long> {
    public User getUserByUsername(String username);
<<<<<<< Updated upstream
=======
    public User getUserByUsernameAndPassword(String username, String password);
>>>>>>> Stashed changes
}
