package org.example.Repository;

import org.example.Domain.User;

public interface UserRepositoryInterface extends RepoInterface<User,Long> {
    public User getUserByUsername(String username);
}
