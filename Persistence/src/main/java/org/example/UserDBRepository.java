package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserDBRepository implements UserRepositoryInterface {
    private static final Logger logger = LogManager.getLogger(UserDBRepository.class);
    private final JdbcUtils dbUtils;

    public UserDBRepository(Properties properties) {
        logger.info("UserDBRepository initialized with properties: {}", properties);
        dbUtils = new JdbcUtils(properties);
    }

    @Override
    public Long insert(User entity) {
        logger.info("Inserting new user");
        Connection conn = dbUtils.getConnection();

        try (PreparedStatement prepStmt = conn.prepareStatement(
                "INSERT INTO user(username, parola) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            prepStmt.setString(1, entity.getUsername());
            prepStmt.setString(2, entity.getPassword());
            int result = prepStmt.executeUpdate();

            if (result > 0) {
                try (ResultSet rs = prepStmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        long id = rs.getLong(1);
                        logger.info("User inserted successfully with id {}", id);
                        return id;
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Error at insert: " + e.getMessage());
            throw new RuntimeException("Error inserting user");
        }
        return null;
    }

    @Override
    public void updateById(Long id, User entity) {
        logger.info("Updating user with id {}", id);
        Connection conn = dbUtils.getConnection();

        try (PreparedStatement prepStmt = conn.prepareStatement(
                "UPDATE user SET username = ?, parola = ? WHERE id = ?")) {
            prepStmt.setString(1, entity.getUsername());
            prepStmt.setString(2, entity.getPassword());
            prepStmt.setLong(3, id);
            int updated = prepStmt.executeUpdate();
            if (updated > 0) {
                logger.info("User updated successfully");
            } else {
                logger.warn("No user found to update with id {}", id);
            }
        } catch (SQLException e) {
            logger.error("Error at updateById: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting user with id {}", id);
        Connection conn = dbUtils.getConnection();

        try (PreparedStatement prepStmt = conn.prepareStatement("DELETE FROM user WHERE id = ?")) {
            prepStmt.setLong(1, id);
            int result = prepStmt.executeUpdate();
            if (result > 0) {
                logger.info("User deleted successfully");
            } else {
                logger.info("No user found with id {}", id);
            }
        } catch (SQLException e) {
            logger.error("Error at deleteById: " + e.getMessage());
        }
    }

    @Override
    public User getById(Long id) {
        logger.info("Retrieving user by id {}", id);
        Connection conn = dbUtils.getConnection();
        User user = null;

        try (PreparedStatement prepStmt = conn.prepareStatement("SELECT * FROM user WHERE id = ?")) {
            prepStmt.setLong(1, id);
            try (ResultSet rs = prepStmt.executeQuery()) {
                if (rs.next()) {
                    user = new User(rs.getLong("id"), rs.getString("username"), rs.getString("parola"));
                    logger.info("User found: {}", user.getUsername());
                }
            }
        } catch (SQLException e) {
            logger.error("Error at getById: " + e.getMessage());
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        logger.info("Retrieving all users");
        Connection conn = dbUtils.getConnection();
        List<User> users = new ArrayList<>();

        try (PreparedStatement prepStmt = conn.prepareStatement("SELECT * FROM user");
             ResultSet rs = prepStmt.executeQuery()) {
            while (rs.next()) {
                User user = new User(rs.getLong("id"), rs.getString("username"), rs.getString("parola"));
                users.add(user);
            }
            logger.info("Retrieved {} users", users.size());
        } catch (SQLException e) {
            logger.error("Error at getAll: " + e.getMessage());
        }
        return users;
    }

    @Override
    public User getUserByUsername(String username) {
        logger.info("Retrieving user with username {}", username);
        Connection conn = dbUtils.getConnection();
        User user = null;

        try (PreparedStatement prepStmt = conn.prepareStatement("SELECT * FROM user WHERE username = ?")) {
            prepStmt.setString(1, username);
            try (ResultSet rs = prepStmt.executeQuery()) {
                if (rs.next()) {
                    user = new User(rs.getLong("id"), rs.getString("username"), rs.getString("parola"));
                    logger.info("User found: {}", username);
                }
            }
        } catch (SQLException e) {
            logger.error("Error at getUserByUsername: " + e.getMessage());
        }
        return user;
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        logger.info("Retrieving user with username {} and password", username);
        Connection conn = dbUtils.getConnection();
        User user = null;

        try (PreparedStatement prepStmt = conn.prepareStatement("SELECT * FROM user WHERE username = ? AND parola = ?")) {
            prepStmt.setString(1, username);
            prepStmt.setString(2, password);
            try (ResultSet rs = prepStmt.executeQuery()) {
                if (rs.next()) {
                    user = new User(rs.getLong("id"), rs.getString("username"), rs.getString("parola"));
                    logger.info("User found: {}", username);
                }
            }
        } catch (SQLException e) {
            logger.error("Error at getUserByUsernameAndPassword: " + e.getMessage());
        }
        return user;
    }
}
