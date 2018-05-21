package com.gft.dbbenchmark.controller;

import com.gft.dbbenchmark.config.ClientDatabaseContextHolder;
import com.gft.dbbenchmark.service.TownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BenchmarkController {

    private final TownService townService;

    @Autowired
    BenchmarkController(TownService townService) {
        this.townService = townService;
    }

    @RequestMapping(value = "/benchmark", method = RequestMethod.GET)
    public String benchmarkHome() {
        return "benchmark";
    }

    @RequestMapping(value = "/benchmark", method = RequestMethod.POST, produces = "application/json")
    public String prepareBenchmarkReport(HttpServletRequest request, Model model) {
        String query = request.getParameter("sql");
        long timeH2 = townService.executeBenchmark(ClientDatabaseContextHolder.ClientDatabaseEnum.H2, query);
        long timeMYSQL = townService.executeBenchmark(ClientDatabaseContextHolder.ClientDatabaseEnum.MYSQL, query);
        model.addAttribute("h2time", timeH2);
        model.addAttribute("mysqltime", timeMYSQL);
        return "benchmark-report";
    }
}
