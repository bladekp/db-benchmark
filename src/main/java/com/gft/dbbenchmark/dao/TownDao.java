package com.gft.dbbenchmark.dao;

import com.gft.dbbenchmark.model.Town;

import java.util.List;

public interface TownDao {

    Town getOne(Long id);

    void saveAll(final List<Town> townList);

    void clearAll();

    void execute(String query);
}
