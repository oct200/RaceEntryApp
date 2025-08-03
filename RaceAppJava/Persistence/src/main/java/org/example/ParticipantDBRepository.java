package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.model.Participant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import java.util.List;

public class ParticipantDBRepository implements ParticipantRepositoryInterface {
    private static final Logger logger = LogManager.getLogger(CursaDBRepository.class);
    private JdbcUtils dbUtils;

    public ParticipantDBRepository(Properties properties) {
        logger.info("Initializing AngajatDBRepository with properties: {}", properties);
        dbUtils = new JdbcUtils(properties);
    }

    @Override
    public Long insert(Participant entity) {
        logger.traceEntry("Inserting new Participant");
        Connection conn = dbUtils.getConnection();

        try (PreparedStatement prepStmt = conn.prepareStatement("INSERT INTO Participant(nume, capMotor, echipa, cnp) VALUES (?,?,?,?)")) {
            prepStmt.setString(1, entity.getNume());
            prepStmt.setInt(2, entity.getCapMotor());
            prepStmt.setString(3, entity.getEchipa());
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
        return new Participant(1L,"err",1234,"err","err");
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
