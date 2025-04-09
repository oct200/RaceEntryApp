package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {
    private Properties jdbcProperties;
    private static final Logger logger = LogManager.getLogger();

    public JdbcUtils(Properties jdbcProperties) {
        this.jdbcProperties = jdbcProperties;
    }

    private Connection instance = null;
    private Connection getNewConnection() {
        logger.traceEntry();
        String url = jdbcProperties.getProperty("url");
        logger.info("Connecting to {}", url);
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Error connecting to database");
        }
        return connection;
    }

    public Connection getConnection() {
        logger.traceEntry();
        try {
            if (instance == null || instance.isClosed())
                instance = getNewConnection();
        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Error connecting to database");
        }
        logger.traceExit(instance);
        return instance;
    }
}
