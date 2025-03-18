package org.example;

import org.example.Repository.UserDBRepository;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        final Properties dbProperties = new Properties();
        try {
            dbProperties.load(new FileReader("db.config"));
        } catch (IOException ex) {
            System.err.println("Could not load properties file " + ex);
        }
        UserDBRepository userDBRepository = new UserDBRepository(dbProperties);
        TesteDB testeDB = new TesteDB();
        testeDB.testeInsertRepoUser(userDBRepository);
        System.out.println("TesteleRuleaza");
    }
}