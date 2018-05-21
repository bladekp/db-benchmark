package com.gft.dbbenchmark.controller;

import com.gft.dbbenchmark.config.ClientDatabaseContextHolder;
import com.gft.dbbenchmark.service.TownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

@Controller
public class BenchmarkController {

    private static final int TEST_COUNT = 1000;
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
        final String query = request.getParameter("sql");
        Map<ClientDatabaseContextHolder.ClientDatabaseEnum, Long> timeMap = IntStream
                .range(0, TEST_COUNT)
                .mapToObj((i) -> {
                    Map<ClientDatabaseContextHolder.ClientDatabaseEnum, Long> map = new HashMap<>();
                    for (ClientDatabaseContextHolder.ClientDatabaseEnum db : ClientDatabaseContextHolder.ClientDatabaseEnum.values()) {
                        map.put(db, townService.executeBenchmark(db, query));
                    }
                    return map;
                })
                .reduce(
                        (map1, map2) -> {
                            for (ClientDatabaseContextHolder.ClientDatabaseEnum db : ClientDatabaseContextHolder.ClientDatabaseEnum.values()) {
                                Long map1db = map1.get(db);
                                Long map2db = map2.get(db);
                                map1.put(db, map1db + map2db);
                            }
                            return map1;
                        }
                )
                .get();
        model.addAttribute("timeMap", timeMap);
        return "benchmark-report";
    }
}
