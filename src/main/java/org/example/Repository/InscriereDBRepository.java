package org.example.Repository;

import org.example.Domain.Inscriere;
import org.example.Domain.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import java.util.List;

public class InscriereDBRepository implements InscriereRepositoryInterface {
    private static final Logger logger = LogManager.getLogger(CursaDBRepository.class);
    private JdbcUtils dbUtils;

    public InscriereDBRepository(Properties properties) {
        logger.info("Initializing AngajatDBRepository with properties: {}", properties);
        dbUtils = new JdbcUtils(properties);
    }
    @Override
    public Pair<Long, Long> insert(Inscriere entity) {

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
}
