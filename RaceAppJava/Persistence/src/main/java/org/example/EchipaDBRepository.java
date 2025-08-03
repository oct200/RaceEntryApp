package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import java.util.List;


public class EchipaDBRepository implements EchipaRepositoryInterface{
    private static final Logger logger = LogManager.getLogger(CursaDBRepository.class);
    private JdbcUtils dbUtils;

    public EchipaDBRepository(Properties properties) {
        logger.info("Initializing AngajatDBRepository with properties: {}", properties);
        dbUtils = new JdbcUtils(properties);
    }

    @Override
    public Long insert(Echipa entity)  {
        logger.traceEntry("Inserting new Echipa");
        Connection conn = dbUtils.getConnection();

        try (PreparedStatement prepStmt = conn.prepareStatement("INSERT INTO Echipa(nume) VALUES (?)")) {
            prepStmt.setString(1, entity.getNume());
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
    public void updateById(Long aLong, Echipa entity) {

    }


    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public Echipa getById(Long aLong) {
        logger.info("starting getById Echipa");
        List<Participant> participants = new ArrayList<>();
        Connection conn = dbUtils.getConnection();
        try (PreparedStatement prepStmt = conn.prepareStatement("SELECT * FROM Echipa WHERE id = ?")) {
            prepStmt.setLong(1, aLong);
            ResultSet rs = prepStmt.executeQuery();
            if (rs.next()) {
                return new Echipa(rs.getLong(1),rs.getString(2));
            }
            logger.info("getById Echipa terminat");
        } catch (Exception e) {
            logger.error("eraore la getById Echipa " + e.getMessage());
        }
        return null;
    }


    @Override
    public List<Echipa> getAll() {
        return List.of();
    }
}
