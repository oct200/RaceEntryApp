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
    EchipaDBRepository ecipaDBRepository;

    public ParticipantDBRepository(Properties properties, EchipaDBRepository ecipaDBRepository) {
        logger.info("Initializing AngajatDBRepository with properties: {}", properties);
        dbUtils = new JdbcUtils(properties);
        this.ecipaDBRepository = ecipaDBRepository;
    }

    @Override
    public Long insert(Participant entity) {
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

