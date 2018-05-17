package com.gft.dbbenchmark.dao;

import com.gft.dbbenchmark.model.Town;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class TownDao {

    private JdbcTemplate jdbcTemplate;

    public TownDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Town getOne(Long id) {
        return jdbcTemplate.queryForObject(
                "select id, name, population from town where id = ?",
                new Object[]{id},
                (rs, rowNum) -> Town
                        .builder()
                        .name(rs.getString("name"))
                        .population(rs.getLong("population"))
                        .id(rs.getLong("id"))
                        .build()
        );
    }

}
