package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.model.Cursa;

<<<<<<< Updated upstream
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
=======
import java.sql.*;
>>>>>>> Stashed changes
import java.util.ArrayList;
import java.util.Properties;

import java.util.List;

<<<<<<< Updated upstream
public class CursaDBRepository implements CursaRepositoryInterface{
    private static final Logger logger = LogManager.getLogger(CursaDBRepository.class);
    private JdbcUtils dbUtils;

    public CursaDBRepository(Properties properties) {
        logger.info("Initializing AngajatDBRepository with properties: {}", properties);
        dbUtils = new JdbcUtils(properties);
    }
    @Override
    public Long insert(Cursa entity) {
        logger.traceEntry("Inserting new Participant");
        Connection conn = dbUtils.getConnection();

        try (PreparedStatement prepStmt = conn.prepareStatement("INSERT INTO Participant(numarParticipanti,capMotor) VALUES (?,?)")) {
            prepStmt.setInt(1, entity.getNumarParticipanti());
            prepStmt.setInt(2, entity.getCapMotor());
            prepStmt.executeUpdate();
            ResultSet rs = prepStmt.getGeneratedKeys();
            if (rs.next()) {
                logger.traceExit("Inserted new Participant with id{}", rs.getLong(1));
                return rs.getLong(1);
            }
        } catch (SQLException ex) {
            logger.error("error at insertion: " + ex);
            System.out.println("DB error: " + ex);
=======
public class CursaDBRepository implements CursaRepositoryInterface {
    private static final Logger logger = LogManager.getLogger(CursaDBRepository.class);
    private final JdbcUtils dbUtils;

    public CursaDBRepository(Properties properties) {
        dbUtils = new JdbcUtils(properties);
        logger.info("CursaDBRepository initialized");
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
            return capacitati;
        } catch (SQLException e) {
            logger.error("Error fetching capacities: " + e.getMessage());
            throw new RuntimeException("Error fetching capacities");
        }
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
>>>>>>> Stashed changes
        }
        return null;
    }

    @Override
<<<<<<< Updated upstream
    public void updateById(Long aLong, Cursa entity) {

    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public Cursa getById(Long aLong) {
        return null;
=======
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
>>>>>>> Stashed changes
    }

    @Override
    public List<Cursa> getAll() {
<<<<<<< Updated upstream
        logger.info("starting getAll Curse");
        List<Cursa> curse = new ArrayList<>();
        Connection conn = dbUtils.getConnection();
        try (PreparedStatement prepStmt = conn.prepareStatement("SELECT * FROM Curse")){
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                curse.add(new Cursa(rs.getLong(1),rs.getInt(2),rs.getInt(3)));
            }
            logger.info("getAll Curse terminat");
            return curse;
        } catch (Exception e) {
            logger.error("eraore la getAll Curse " + e.getMessage());
        }
        return null;
    }
}
=======
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
>>>>>>> Stashed changes
