package org.example.Repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Domain.Cursa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        return List.of();
    }
}
