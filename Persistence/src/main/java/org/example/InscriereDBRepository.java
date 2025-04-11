package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.model.Cursa;
import org.model.Inscriere;
import org.model.Pair;
import org.model.Participant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import java.util.List;

public class InscriereDBRepository implements InscriereRepositoryInterface {
    private static final Logger logger = LogManager.getLogger(InscriereDBRepository.class);
    private final JdbcUtils dbUtils;
    private final ParticipantRepositoryInterface pRepo;
    private final CursaRepositoryInterface cRepo;

    public InscriereDBRepository(Properties properties, ParticipantRepositoryInterface pRepo, CursaRepositoryInterface cRepo) {
        dbUtils = new JdbcUtils(properties);
        this.pRepo = pRepo;
        this.cRepo = cRepo;
        logger.info("InscriereDBRepository initialized");
    }

    @Override
    public Pair<Long, Long> insert(Inscriere entity) {
        logger.info("Inserting new Inscriere");
        Connection conn = dbUtils.getConnection();

        try (PreparedStatement prepStmt = conn.prepareStatement(
                "INSERT INTO inscriere (cursa_id, participant_id) VALUES (?, ?)")) {
            prepStmt.setLong(1, entity.getCursa().getId());
            prepStmt.setLong(2, entity.getParticipant().getId());
            prepStmt.executeUpdate();
            logger.info("Inserted new Inscriere successfully");
            return entity.getId();
        } catch (SQLException e) {
            logger.error("Error inserting Inscriere: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void updateById(Pair<Long, Long> id, Inscriere entity) {
        // Not implemented
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(Pair<Long, Long> id) {
        // Not implemented
        throw new UnsupportedOperationException();
    }

    @Override
    public Inscriere getById(Pair<Long, Long> id) {
        // Not implemented
        return null;
    }

    @Override
    public List<Inscriere> getAll() {
        // Not implemented
        return new ArrayList<>();
    }

    @Override
    public List<Participant> getParticipantiInscrisiByCursaId(Long id) {
        logger.info("Fetching Participants for CursaId: {}", id);
        Connection conn = dbUtils.getConnection();
        List<Participant> participants = new ArrayList<>();

        try (PreparedStatement prepStmt = conn.prepareStatement(
                "SELECT * FROM inscriere WHERE cursa_id = ?")) {
            prepStmt.setLong(1, id);
            try (ResultSet rs = prepStmt.executeQuery()) {
                while (rs.next()) {
                    Long participantId = rs.getLong("participant_id");
                    Participant participant = pRepo.getById(participantId);
                    participants.add(participant);
                }
            }
            logger.info("Participants fetched successfully");
        } catch (SQLException e) {
            logger.error("Error fetching Participants: " + e.getMessage());
        }
        return participants;
    }

    @Override
    public List<Cursa> getCursePosibilePentruParticipant(Participant participant) {
        logger.info("getCursePosibilePentruParticipant {}", participant.getId());
        Connection conn = dbUtils.getConnection();
        List<Cursa> curse = new ArrayList<>();

        try (PreparedStatement prepStmt = conn.prepareStatement(
                "SELECT C.id, C.numarParticipanti, C.capMotor " +
                        "FROM Cursa C " +
                        "WHERE C.capMotor = ? " +
                        "AND C.id NOT IN (SELECT cursa_id FROM inscriere WHERE participant_id = ?)")) {
            prepStmt.setInt(1, participant.getCapMotor());
            prepStmt.setLong(2, participant.getId());
            try (ResultSet rs = prepStmt.executeQuery()) {
                while (rs.next()) {
                    curse.add(new Cursa(
                            rs.getLong("id"),
                            rs.getInt("numarParticipanti"),
                            rs.getInt("capMotor")
                    ));
                }
            }
            logger.info("Curse fetched successfully");
        } catch (SQLException e) {
            logger.error("Error fetching getCursePosibilePentruParticipant {} : {}", participant.getId(), e.getMessage());
        }
        return curse;
    }
}

