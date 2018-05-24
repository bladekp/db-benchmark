package pl.bladekp.dbbenchmark.controller;

import pl.bladekp.dbbenchmark.config.ClientDatabaseContextHolder;
import pl.bladekp.dbbenchmark.service.TownService;
import lombok.ToString;
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
        model.addAttribute("timeMap", executeBenchmark(request.getParameter("sql")));
        return "benchmark-report";
    }

    private Map<ClientDatabaseContextHolder.ClientDatabaseEnum, BenchmarkStatistics> executeBenchmark(final String query) {
        return IntStream
                .range(0, TEST_COUNT)
                .mapToObj((i) -> {
                    Map<ClientDatabaseContextHolder.ClientDatabaseEnum, BenchmarkStatistics> map = new HashMap<>();
                    for (ClientDatabaseContextHolder.ClientDatabaseEnum db : ClientDatabaseContextHolder.ClientDatabaseEnum.values()) {
                        map.put(db, new BenchmarkStatistics(1, townService.executeBenchmark(db, query)));
                    }
                    return map;
                })
                .reduce(
                        (map1, map2) -> {
                            for (ClientDatabaseContextHolder.ClientDatabaseEnum db : ClientDatabaseContextHolder.ClientDatabaseEnum.values()) {
                                BenchmarkStatistics benchmark1 = map1.get(db);
                                BenchmarkStatistics benchmark2 = map2.get(db);
                                map1.put(db,
                                        new BenchmarkStatistics(benchmark1.totalOperations + benchmark2.totalOperations, benchmark1.totalOperationsTime + benchmark2.totalOperationsTime)
                                );
                            }
                            return map1;
                        }
                )
                .get();
    }

    @ToString
    private class BenchmarkStatistics {
        private final long totalOperations;
        private final long totalOperationsTime;
        private final double operationsPerSecond;

        private BenchmarkStatistics(long totalOperations, long totalOperationsTime) {
            this.totalOperations = totalOperations;
            this.totalOperationsTime = totalOperationsTime;
            double seconds = totalOperationsTime / 1000.0;
            if (seconds > 0L) {
                this.operationsPerSecond = totalOperations / seconds;
            } else {
                this.operationsPerSecond = Double.MAX_VALUE;
            }
        }
    }
}
