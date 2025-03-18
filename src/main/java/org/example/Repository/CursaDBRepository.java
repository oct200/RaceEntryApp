package org.example.Repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Domain.Cursa;
import org.example.Domain.Participant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import java.util.List;

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
        }
        return null;
    }

    @Override
    public void updateById(Long aLong, Cursa entity) {

    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public Cursa getById(Long aLong) {
        return null;
    }

    @Override
    public List<Cursa> getAll() {
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
