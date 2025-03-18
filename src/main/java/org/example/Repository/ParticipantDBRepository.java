package org.example.Repository;

import org.example.Domain.Participant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import java.util.List;

public class ParticipantDBRepository implements ParticipantRepositoryInterface{
    private static final Logger logger = LogManager.getLogger(CursaDBRepository.class);
    private JdbcUtils dbUtils;

    public ParticipantDBRepository(Properties properties) {
        logger.info("Initializing AngajatDBRepository with properties: {}", properties);
        dbUtils = new JdbcUtils(properties);
    }
    @Override
    public Long insert(Participant entity) {

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
        return null;
    }

    @Override
    public List<Participant> getAll() {
        return List.of();
    }
}
