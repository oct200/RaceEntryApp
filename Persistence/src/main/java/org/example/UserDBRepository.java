package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.model.User;

<<<<<<< Updated upstream
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
=======
import java.sql.*;
import java.util.ArrayList;
>>>>>>> Stashed changes
import java.util.Properties;

import java.util.List;

public class UserDBRepository implements UserRepositoryInterface {
<<<<<<< Updated upstream
    private static final Logger logger = LogManager.getLogger(CursaDBRepository.class);
    private JdbcUtils dbUtils;

    public UserDBRepository(Properties properties) {
        logger.info("Initializing AngajatDBRepository with properties: {}", properties);
        dbUtils = new JdbcUtils(properties);
    }
    @Override
    public User getUserByUsername(String username) {
        logger.traceEntry("Inserting new user");
        Connection conn = dbUtils.getConnection();

        try (PreparedStatement prepStmt = conn.prepareStatement("SELECT FROM User WHERE username = ? ")) {
            ResultSet rs = prepStmt.executeQuery();
            if (rs.next()) {
                logger.traceEntry("Found user with username: {}", username);
                rs.close();
                return new User(rs.getLong(1), rs.getString(2), rs.getString(3));
            } else {
                logger.traceEntry("No user found with username: {}", username);
                rs.close();
                return null;
            }
        }
        catch (SQLException e) {
            logger.error("eroare la getUserByUsername " + e.getMessage());
            return null;
        }
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
=======
    private static final Logger logger = LogManager.getLogger(UserDBRepository.class);
    private final JdbcUtils dbUtils;

    public UserDBRepository(Properties properties) {
        logger.info("UserDBRepository initialized with properties: {}", properties);
        dbUtils = new JdbcUtils(properties);
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
>>>>>>> Stashed changes
    }

    @Override
    public List<User> getAll() {
<<<<<<< Updated upstream
        return List.of();
    }
}
=======
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
    public User getById(Long id) {
        logger.info("Retrieving user by id {}", id);
        Connection conn = dbUtils.getConnection();
        User user = null;

        try (PreparedStatement prepStmt = conn.prepareStatement("SELECT * FROM Uuer WHERE id = ?")) {
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

    }
}

>>>>>>> Stashed changes
