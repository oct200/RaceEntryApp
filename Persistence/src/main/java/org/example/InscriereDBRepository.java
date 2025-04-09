package org.example;

<<<<<<< Updated upstream:src/main/java/org/example/Repository/InscriereDBRepository.java
import org.example.Domain.Echipa;
import org.example.Domain.Inscriere;
import org.example.Domain.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Domain.Participant;
=======
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.model.Cursa;
import org.model.Inscriere;
import org.model.Pair;
import org.model.Participant;
>>>>>>> Stashed changes:Persistence/src/main/java/org/example/InscriereDBRepository.java

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import java.util.List;

public class InscriereDBRepository implements InscriereRepositoryInterface {
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
        return null;
    }

    @Override
    public List<Inscriere> getAll() {
        return List.of();
    }

    @Override
    public List<Participant> getParticipantiInscrisiByCursaId(Long id) {
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
