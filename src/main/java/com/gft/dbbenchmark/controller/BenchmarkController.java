package com.gft.dbbenchmark.controller;

import com.gft.dbbenchmark.config.ClientDatabaseContextHolder;
import com.gft.dbbenchmark.service.TownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BenchmarkController {

    private final TownService townService;

    @Autowired
    BenchmarkController(TownService townService){
        this.townService = townService;
    }

    @RequestMapping(value="/benchmark/report", method = RequestMethod.GET)//, produces = "application/json")
    public String prepareBenchmarkReport(){
        String h2 = townService.getFirstTownName(ClientDatabaseContextHolder.ClientDatabaseEnum.H2);
        String mysql = townService.getFirstTownName(ClientDatabaseContextHolder.ClientDatabaseEnum.MYSQL);
        return "H2: " + h2 + " MYSQL: "+ mysql;
    }
}
