package pl.bladekp.dbbenchmark.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.bladekp.dbbenchmark.config.ClientDatabaseContextHolder;
import pl.bladekp.dbbenchmark.model.Town;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        Stream
                .of(ClientDatabaseContextHolder.ClientDatabaseEnum.values())
                .filter(ClientDatabaseContextHolder.ClientDatabaseEnum::isEnabled)
                .forEach(db -> initialize(db, townList));
    }

    private void initialize(ClientDatabaseContextHolder.ClientDatabaseEnum clientDatabase, List<Town> townList) {
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
