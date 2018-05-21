package com.gft.dbbenchmark.dao;

import com.gft.dbbenchmark.model.Town;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TownDao {

    private JdbcTemplate jdbcTemplate;

    public TownDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Town getOne(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT id, name, population FROM town WHERE id = ?",
                new Object[]{id},
                (rs, rowNum) -> Town
                        .builder()
                        .name(rs.getString("name"))
                        .population(rs.getLong("population"))
                        .id(rs.getLong("id"))
                        .build()
        );
    }

    public void saveAll(final List<Town> townList) {
        jdbcTemplate.batchUpdate(
                "INSERT INTO town(name, population) VALUES (?, ?)",
                new BatchPreparedStatementSetter() {

                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                        Town town = townList.get(i);
                        preparedStatement.setString(1, town.getName());
                        preparedStatement.setLong(2, town.getPopulation());
                    }

                    @Override
                    public int getBatchSize() {
                        return townList.size();
                    }
                });
    }

    public void clearAll(){
        jdbcTemplate.update("DELETE FROM town");
    }

    public void execute(String query) {
        jdbcTemplate.execute(query);
    }

}
