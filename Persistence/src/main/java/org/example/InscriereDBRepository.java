package org.example;

<<<<<<< Updated upstream
<<<<<<< Updated upstream:src/main/java/org/example/Repository/InscriereDBRepository.java
import org.example.Domain.Echipa;
import org.example.Domain.Inscriere;
import org.example.Domain.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Domain.Participant;
=======
=======
>>>>>>> Stashed changes
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.model.Cursa;
import org.model.Inscriere;
import org.model.Pair;
import org.model.Participant;
<<<<<<< Updated upstream
>>>>>>> Stashed changes:Persistence/src/main/java/org/example/InscriereDBRepository.java
=======
>>>>>>> Stashed changes

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import java.util.List;

public class InscriereDBRepository implements InscriereRepositoryInterface {
<<<<<<< Updated upstream
    private static final Logger logger = LogManager.getLogger(CursaDBRepository.class);
    private JdbcUtils dbUtils;
    ParticipantDBRepository pRepo;
    EchipaDBRepository eRepo;

    public InscriereDBRepository(Properties properties,ParticipantDBRepository pdbRepo,EchipaDBRepository peRepo) {
        logger.info("Initializing AngajatDBRepository with properties: {}", properties);
        dbUtils = new JdbcUtils(properties);
        this.pRepo = pdbRepo;
        this.eRepo = peRepo;
    }
    @Override
    public Pair<Long, Long> insert(Inscriere entity) {
        logger.traceEntry("Inserting new Participant");
        Connection conn = dbUtils.getConnection();

        try (PreparedStatement prepStmt = conn.prepareStatement("INSERT INTO Participant(cursa_id,participant_id) VALUES (?,?)")) {
            prepStmt.setLong(1,entity.getCursa().getId());
            prepStmt.setLong(2,entity.getParticipant().getId());
            prepStmt.executeUpdate();
            return entity.getId();
        } catch (SQLException ex) {
            logger.error("error at insertion: " + ex);
            System.out.println("DB error: " + ex);
        }
        return null;
    }

    @Override
    public void updateById(Pair<Long, Long> longLongPair, Inscriere entity) {

    }

    @Override
    public void deleteById(Pair<Long, Long> longLongPair) {

    }

    @Override
    public Inscriere getById(Pair<Long, Long> longLongPair) {
=======
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
>>>>>>> Stashed changes
        return null;
    }

    @Override
    public List<Inscriere> getAll() {
<<<<<<< Updated upstream
        return List.of();
=======
        // Not implemented
        return new ArrayList<>();
>>>>>>> Stashed changes
    }

    @Override
    public List<Participant> getParticipantiInscrisiByCursaId(Long id) {
<<<<<<< Updated upstream
        logger.info("starting getParticipantiInscrisiByCursaId");
        List<Participant> participants = new ArrayList<>();
        Connection conn = dbUtils.getConnection();
        try (PreparedStatement prepStmt = conn.prepareStatement("SELECT * FROM Inscriere WHERE cursa_id = ?")){
            prepStmt.setLong(1,id);
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                participants.add(pRepo.getById(rs.getLong("participant_id")));
            }
            logger.info("getParticipantiInscrisiByCursaId terminat");
            return participants;
        } catch (Exception e) {
            logger.error("eraore la getParticipantiInscrisiByCursaId " + e.getMessage());
        }
        return null;
    }
}
=======
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

>>>>>>> Stashed changes
