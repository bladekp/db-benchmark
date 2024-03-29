package pl.bladekp.dbbenchmark.controller;

import pl.bladekp.dbbenchmark.config.ClientDatabaseContextHolder;
import pl.bladekp.dbbenchmark.service.DataAccessService;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Controller
public class BenchmarkController {

    private final DataAccessService dataAccessService;

    @Autowired
    BenchmarkController(DataAccessService dataAccessService) {
        this.dataAccessService = dataAccessService;
    }

    @RequestMapping(value = "/benchmark", method = RequestMethod.GET)
    public String benchmarkHome() {
        return "benchmark";
    }

    @Transactional
    @RequestMapping(value = "/benchmark", method = RequestMethod.POST, produces = "application/json")
    public String prepareBenchmarkReport(HttpServletRequest request, Model model) {
        model.addAttribute("timeMap", executeBenchmark(request.getParameter("sql"), request.getParameter("mql"), Integer.valueOf(request.getParameter("count"))));
        return "benchmark-report";
    }

    private Map<ClientDatabaseContextHolder.ClientDatabaseEnum, BenchmarkStatistics> executeBenchmark(final String sql, final String mql, final int count) {
        return IntStream
                .range(0, count)
                .mapToObj((i) -> {
                    Map<ClientDatabaseContextHolder.ClientDatabaseEnum, BenchmarkStatistics> map = new HashMap<>();
                    Stream
                            .of(ClientDatabaseContextHolder.ClientDatabaseEnum.values())
                            .filter(ClientDatabaseContextHolder.ClientDatabaseEnum::isEnabled)
                            .forEach(db ->  map.put(db, new BenchmarkStatistics(db, sql, mql)));
                    return map;
                })
                .reduce(
                        (map1, map2) -> {
                            Stream
                                    .of(ClientDatabaseContextHolder.ClientDatabaseEnum.values())
                                    .filter(ClientDatabaseContextHolder.ClientDatabaseEnum::isEnabled)
                                    .forEach(db -> {
                                        BenchmarkStatistics benchmark1 = map1.get(db);
                                        BenchmarkStatistics benchmark2 = map2.get(db);
                                        map1.put(db,
                                                new BenchmarkStatistics(benchmark1.totalOperations + benchmark2.totalOperations, benchmark1.totalOperationsTime + benchmark2.totalOperationsTime, benchmark1.exception)
                                        );
                                    });
                            return map1;
                        }
                )
                .get();
    }

    @ToString
    private class BenchmarkStatistics {
        private long totalOperations;
        private long totalOperationsTime;
        private double operationsPerSecond;
        private Exception exception;

        private BenchmarkStatistics(long totalOperations, long totalOperationsTime, Exception e) {
            this.exception = e;
            this.totalOperations = totalOperations;
            this.totalOperationsTime = totalOperationsTime;
            double seconds = totalOperationsTime / 1000.0;
            if (seconds > 0L) {
                this.operationsPerSecond = totalOperations / seconds;
            } else {
                this.operationsPerSecond = Double.MAX_VALUE;
            }
        }

        private BenchmarkStatistics(ClientDatabaseContextHolder.ClientDatabaseEnum db, String sql, String mql){
            try {
                long totalOperationsTime = ClientDatabaseContextHolder.execute(() -> {
                    try {
                        return dataAccessService.executeBenchmark(sql, mql, db);
                    } catch (SQLException e){
                        throw new RuntimeException(e);
                    }
                }, db);
                this.exception = null;
                this.totalOperations = 1;
                this.totalOperationsTime = totalOperationsTime;
                double seconds = totalOperationsTime / 1000.0;
                if (seconds > 0L) {
                    this.operationsPerSecond = totalOperations / seconds;
                } else {
                    this.operationsPerSecond = Double.MAX_VALUE;
                }
            } catch (RuntimeException e){
                this.totalOperations = -1;
                this.totalOperationsTime = -1;
                this.operationsPerSecond = -1;
                this.exception = e;
            }
        }
    }
}
