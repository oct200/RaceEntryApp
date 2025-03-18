package org.example.Repository;

import org.example.Domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import java.util.List;

public class UserDBRepository implements UserRepositoryInterface {
    private static final Logger logger = LogManager.getLogger(CursaDBRepository.class);
    private JdbcUtils dbUtils;

    public UserDBRepository(Properties properties) {
        logger.info("Initializing AngajatDBRepository with properties: {}", properties);
        dbUtils = new JdbcUtils(properties);
    }
    @Override
    public User getUserByUsername(String username) {
        return null;
    }

    @Override
    public Long insert(User entity) {
        logger.traceEntry("Inserting new user");
        Connection conn = dbUtils.getConnection();

        try (PreparedStatement prepStmt = conn.prepareStatement("INSERT INTO User(username,parola) VALUES (?,?)")) {
            prepStmt.setString(1, entity.getUsername());
            prepStmt.setString(2, entity.getPassword());
            prepStmt.executeUpdate();
            ResultSet rs = prepStmt.getGeneratedKeys();
            if (rs.next()) {
                logger.traceExit("Inserted new user with id{}", rs.getLong(1));
                return rs.getLong(1);
            }
        } catch (SQLException ex) {
            logger.error("error at insertion: " + ex);
            System.out.println("DB error: " + ex);
        }
        return null;
    }

    @Override
    public void updateById(Long aLong, User entity) {

    }

    @Override
    public void deleteById(Long aLong) {
        logger.traceEntry("Inserting new user");
        Connection conn = dbUtils.getConnection();

        try (PreparedStatement prepStmt = conn.prepareStatement("DELETE FROM User WHERE id = ?")) {
            prepStmt.setLong(1, aLong);
            prepStmt.executeUpdate();
            logger.traceExit("Deleted user with id{}", aLong);
        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("DB error: " + ex);
        }
    }

    @Override
    public User getById(Long aLong) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return List.of();
    }
}
