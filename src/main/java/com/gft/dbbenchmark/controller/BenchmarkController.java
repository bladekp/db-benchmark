package com.gft.dbbenchmark.controller;

import com.gft.dbbenchmark.config.ClientDatabaseContextHolder;
import com.gft.dbbenchmark.model.Town;
import com.gft.dbbenchmark.repo.TownRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BenchmarkController {

    private final TownRepository townRepo;

    @Autowired
    BenchmarkController(TownRepository townRepo){
        this.townRepo = townRepo;
    }

    @RequestMapping(value="/benchmark/report", method = RequestMethod.GET, produces = "application/json")
    public String prepareBenchmarkReport(){
        ClientDatabaseContextHolder.set(ClientDatabaseContextHolder.ClientDatabaseEnum.MYSQL);
        townRepo.save(Town.builder().name("Warsaw").population(1800000L).build());
        ClientDatabaseContextHolder.clear();
        ClientDatabaseContextHolder.set(ClientDatabaseContextHolder.ClientDatabaseEnum.H2);
        townRepo.save(Town.builder().name("Cracow").population(750000L).build());
        ClientDatabaseContextHolder.clear();
        return "{}";
    }
}
