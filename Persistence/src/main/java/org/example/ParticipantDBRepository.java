package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.model.Participant;

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

public class ParticipantDBRepository implements ParticipantRepositoryInterface {
<<<<<<< Updated upstream
    private static final Logger logger = LogManager.getLogger(CursaDBRepository.class);
    private JdbcUtils dbUtils;
    EchipaDBRepository ecipaDBRepository;

    public ParticipantDBRepository(Properties properties, EchipaDBRepository ecipaDBRepository) {
        logger.info("Initializing AngajatDBRepository with properties: {}", properties);
        dbUtils = new JdbcUtils(properties);
        this.ecipaDBRepository = ecipaDBRepository;
=======
    private static final Logger logger = LogManager.getLogger(ParticipantDBRepository.class);
    private final JdbcUtils dbUtils;

    public ParticipantDBRepository(Properties properties) {
        dbUtils = new JdbcUtils(properties);
        logger.info("ParticipantDBRepository initialized");
>>>>>>> Stashed changes
    }

    @Override
    public Long insert(Participant entity) {
<<<<<<< Updated upstream
        logger.traceEntry("Inserting new Participant");
        Connection conn = dbUtils.getConnection();

        try (PreparedStatement prepStmt = conn.prepareStatement("INSERT INTO Participant(nume, capMotor, echipa, cnp) VALUES (?,?,?,?)")) {
            prepStmt.setString(1, entity.getNume());
            prepStmt.setInt(2, entity.getCapMotor());
            prepStmt.setLong(3, entity.getEchipa().getId());
            prepStmt.setString(4, entity.getCnp());
            prepStmt.executeUpdate();
            ResultSet rs = prepStmt.getGeneratedKeys();
            if (rs.next()) {
                logger.traceExit("Inserted new Participant with id{}", rs.getLong(1));
                return rs.getLong(1);
            }
        } catch (SQLException ex) {
            logger.error("error at insertion: " + ex);
            System.out.println("DB error: " + ex);
        }
        return null;
    }

    @Override
    public void updateById(Long aLong, Participant entity) {

    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public Participant getById(Long aLong) {
        logger.info("starting getById Participant");
        List<Participant> participants = new ArrayList<>();
        Connection conn = dbUtils.getConnection();
        try (PreparedStatement prepStmt = conn.prepareStatement("SELECT * FROM Participant WHERE id = ?")) {
            prepStmt.setLong(1, aLong);
            ResultSet rs = prepStmt.executeQuery();
            if (rs.next()) {
                return new Participant(rs.getLong(1), rs.getString(2),rs.getInt(3),ecipaDBRepository.getById(rs.getLong(4)),rs.getString(5));
            }
            logger.info("getById Participant terminat");
        } catch (Exception e) {
            logger.error("eraore la getById Participant " + e.getMessage());
        }
        return null;
    }


    @Override
    public List<Participant> getAll() {
        return List.of();
    }
}

=======
        logger.info("Inserting new Participant");
        Connection conn = dbUtils.getConnection();

        try (PreparedStatement prepStmt = conn.prepareStatement(
                "INSERT INTO participant (nume, capMotor, echipa, cnp) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            prepStmt.setString(1, entity.getNume());
            prepStmt.setInt(2, entity.getCapMotor());
            prepStmt.setString(3, entity.getEchipa());
            prepStmt.setString(4, entity.getCnp());

            int result = prepStmt.executeUpdate();
            if (result > 0) {
                try (ResultSet rs = prepStmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        long id = rs.getLong(1);
                        logger.info("Inserted new Participant with id {}", id);
                        return id;
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Error inserting Participant: " + e.getMessage());
            throw new RuntimeException("Error inserting Participant");
        }
        return -1L;
    }

    @Override
    public void updateById(Long id, Participant entity) {
        // Not implemented
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting Participant with id {}", id);
        Connection conn = dbUtils.getConnection();

        try (PreparedStatement prepStmt = conn.prepareStatement("DELETE FROM participant WHERE id = ?")) {
            prepStmt.setLong(1, id);
            prepStmt.executeUpdate();
            logger.info("Participant deleted successfully");
        } catch (SQLException e) {
            logger.error("Error deleting Participant: " + e.getMessage());
        }
    }

    @Override
    public Participant getById(Long id) {
        logger.info("Fetching Participant with id {}", id);
        Connection conn = dbUtils.getConnection();
        Participant participant = null;

        try (PreparedStatement prepStmt = conn.prepareStatement("SELECT * FROM participant WHERE id = ?")) {
            prepStmt.setLong(1, id);
            try (ResultSet rs = prepStmt.executeQuery()) {
                if (rs.next()) {
                    participant = new Participant(
                            rs.getLong("id"),
                            rs.getString("nume"),
                            rs.getInt("capMotor"),
                            rs.getString("echipa"),
                            rs.getString("cnp")
                    );
                }
            }
        } catch (SQLException e) {
            logger.error("Error fetching Participant: " + e.getMessage());
        }
        return participant;
    }

    @Override
    public List<Participant> getAllByTeam(String team) {
        logger.info("Getting all Participants by team {}", team);
        Connection conn = dbUtils.getConnection();
        List<Participant> participants = new ArrayList<>();

        try (PreparedStatement prepStmt = conn.prepareStatement("SELECT * FROM participant WHERE echipa = ?")) {
            prepStmt.setString(1, team);
            try (ResultSet rs = prepStmt.executeQuery()) {
                while (rs.next()) {
                    participants.add(new Participant(
                            rs.getLong("id"),
                            rs.getString("nume"),
                            rs.getInt("capMotor"),
                            rs.getString("echipa"),
                            rs.getString("cnp")
                    ));
                }
            }
        } catch (SQLException e) {
            logger.error("Error fetching all Participants by team: " + e.getMessage());
        }
        return participants;
    }

    @Override
    public List<Participant> getAll() {
        logger.info("Fetching all Participants");
        Connection conn = dbUtils.getConnection();
        List<Participant> participants = new ArrayList<>();

        try (PreparedStatement prepStmt = conn.prepareStatement("SELECT * FROM participant");
             ResultSet rs = prepStmt.executeQuery()) {
            while (rs.next()) {
                participants.add(new Participant(
                        rs.getLong("id"),
                        rs.getString("nume"),
                        rs.getInt("capMotor"),
                        rs.getString("echipa"),
                        rs.getString("cnp")
                ));
            }
        } catch (SQLException e) {
            logger.error("Error fetching all Participants: " + e.getMessage());
        }
        return participants;
    }

    @Override
    public List<String> getEchipe() {
        logger.info("Fetching all team names");
        Connection conn = dbUtils.getConnection();
        List<String> echipe = new ArrayList<>();

        try (PreparedStatement prepStmt = conn.prepareStatement("SELECT DISTINCT echipa FROM participant");
             ResultSet rs = prepStmt.executeQuery()) {
            while (rs.next()) {
                echipe.add(rs.getString("echipa"));
            }
        } catch (SQLException e) {
            logger.error("Error fetching team names: " + e.getMessage());
            throw new RuntimeException("Error fetching team names");
        }
        return echipe;
    }
}


>>>>>>> Stashed changes
