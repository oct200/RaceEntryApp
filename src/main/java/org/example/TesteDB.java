package org.example;

import org.example.Domain.User;
import org.example.Repository.UserDBRepository;

public class TesteDB {
    public void testeInsertRepoUser(UserDBRepository repo){
        User user1 = new User(Long.valueOf(1),"userTest","pass");
        Long id = repo.insert(new User(Long.valueOf(1),"userTest","pass"));
        repo.deleteById(id);

    }
}
