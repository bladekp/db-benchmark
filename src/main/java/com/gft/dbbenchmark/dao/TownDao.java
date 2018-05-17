package com.gft.dbbenchmark.dao;

import com.gft.dbbenchmark.model.Town;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class TownDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TownDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Town getFirst(){
        Town town = this.jdbcTemplate.queryForObject(
                "select id, name, population from town where id = ?",
                new Object[]{1L},
                new RowMapper<Town>() {
                    public Town mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Town town = Town.builder().name(rs.getString("name")).population(rs.getLong("population")).id(rs.getLong("id")).build();
                        return town;
                    }
                });
        return town;
    }

}
