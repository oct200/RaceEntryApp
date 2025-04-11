package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.model.Cursa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CursaDBRepository implements CursaRepositoryInterface {
    private static final Logger logger = LogManager.getLogger(CursaDBRepository.class);
    private final JdbcUtils dbUtils;

    public CursaDBRepository(Properties properties) {
        dbUtils = new JdbcUtils(properties);
        logger.info("CursaDBRepository initialized");
    }

    @Override
    public Long insert(Cursa entity) {
        logger.info("Inserting new Cursa");
        Connection conn = dbUtils.getConnection();

        try (PreparedStatement prepStmt = conn.prepareStatement(
                "INSERT INTO Cursa (numarParticipanti, capMotor) VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            prepStmt.setInt(1, entity.getNumarParticipanti());
            prepStmt.setInt(2, entity.getCapMotor());
            prepStmt.executeUpdate();

            try (ResultSet generatedKeys = prepStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long id = generatedKeys.getLong(1);
                    logger.info("Inserted new Cursa with id {}", id);
                    return id;
                }
            }
        } catch (SQLException e) {
            logger.error("Error inserting Cursa: " + e.getMessage());
        }
        return -1L;
    }

    @Override
    public void updateById(Long id, Cursa entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Cursa getById(Long id) {
        logger.info("Fetching Cursa with id {}", id);
        Connection conn = dbUtils.getConnection();

        try (PreparedStatement prepStmt = conn.prepareStatement("SELECT * FROM cursa WHERE id = ?")) {
            prepStmt.setLong(1, id);
            try (ResultSet rs = prepStmt.executeQuery()) {
                if (rs.next()) {
                    return new Cursa(rs.getLong("id"), rs.getInt("numarParticipanti"), rs.getInt("capMotor"));
                }
            }
        } catch (SQLException e) {
            logger.error("Error fetching Cursa: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Cursa> getAll() {
        logger.info("Fetching all Curse");
        List<Cursa> curse = new ArrayList<>();
        Connection conn = dbUtils.getConnection();

        try (PreparedStatement prepStmt = conn.prepareStatement("SELECT * FROM cursa");
             ResultSet rs = prepStmt.executeQuery()) {

            while (rs.next()) {
                curse.add(new Cursa(rs.getLong("id"), rs.getInt("numarParticipanti"), rs.getInt("capMotor")));
            }
        } catch (SQLException e) {
            logger.error("Error fetching all Curse: " + e.getMessage());
        }
        logger.info("Returning " + curse.size() + " curse");
        return curse;
    }

    @Override
    public List<Integer> getCapacitati() {
        logger.info("Fetching all motor capacities");
        List<Integer> capacitati = new ArrayList<>();
        Connection conn = dbUtils.getConnection();

        try (PreparedStatement prepStmt = conn.prepareStatement("SELECT DISTINCT capMotor FROM cursa");
             ResultSet rs = prepStmt.executeQuery()) {

            while (rs.next()) {
                capacitati.add(rs.getInt("capMotor"));
            }
        } catch (SQLException e) {
            logger.error("Error fetching capacities: " + e.getMessage());
            throw new RuntimeException("Error fetching capacities");
        }
        return capacitati;
    }

    @Override
    public void increaseNr(Long id) {
        logger.info("Increasing numarParticipanti for Cursa with id {}", id);
        Connection conn = dbUtils.getConnection();

        try (PreparedStatement prepStmt = conn.prepareStatement(
                "UPDATE cursa SET numarParticipanti = numarParticipanti + 1 WHERE id = ?")) {
            prepStmt.setLong(1, id);
            prepStmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error increasing numarParticipanti for id {}: {}", id, e.getMessage());
        }
    }

    @Override
    public List<Cursa> getCurseByCap(int cap) {
        logger.info("Fetching all Curse with motor capacity {}", cap);
        List<Cursa> curse = new ArrayList<>();
        Connection conn = dbUtils.getConnection();

        try (PreparedStatement prepStmt = conn.prepareStatement("SELECT * FROM cursa WHERE capMotor = ?")) {
            prepStmt.setInt(1, cap);
            try (ResultSet rs = prepStmt.executeQuery()) {
                while (rs.next()) {
                    curse.add(new Cursa(rs.getLong("id"), rs.getInt("numarParticipanti"), rs.getInt("capMotor")));
                }
            }
        } catch (SQLException e) {
            logger.error("Error fetching Curse by capMotor: " + e.getMessage());
        }
        logger.info("Returning " + curse.size() + " curse");
        return curse;
    }
}
