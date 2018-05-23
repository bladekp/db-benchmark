package com.gft.dbbenchmark.service;

import com.gft.dbbenchmark.config.ClientDatabaseContextHolder;
import com.gft.dbbenchmark.model.Town;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class DataFeederService {

    private final TownService townService;

    @Autowired
    DataFeederService(TownService townService) {
        this.townService = townService;
    }

    @PostConstruct
    public void initialize() {
        List<Town> townList = createRandomTowns();
        initiialize(ClientDatabaseContextHolder.ClientDatabaseEnum.H2, townList);
        initiialize(ClientDatabaseContextHolder.ClientDatabaseEnum.MYSQL, townList);
        initiialize(ClientDatabaseContextHolder.ClientDatabaseEnum.POSTGRESQL, townList);
    }

    private void initiialize(ClientDatabaseContextHolder.ClientDatabaseEnum clientDatabase, List<Town> townList) {
        townService.clearAll(clientDatabase);
        townService.saveAll(clientDatabase, townList);
    }

    private List<Town> createRandomTowns() {
        return new Random()
                .longs(100L)
                .mapToObj(i -> Town.builder().population(Math.abs(i)).name(RandomStringUtils.randomAlphabetic(10)).build())
                .collect(Collectors.toList());
    }
}
