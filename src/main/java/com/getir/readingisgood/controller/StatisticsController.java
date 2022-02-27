package com.getir.readingisgood.controller;

import com.getir.readingisgood.model.dto.MonthlyStatisticsDTO;
import com.getir.readingisgood.model.response.SuccessResponse;
import com.getir.readingisgood.service.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/statistics/monthly")
    public ResponseEntity<SuccessResponse<List<MonthlyStatisticsDTO>>> getMonthlyStatistics() {
        List<MonthlyStatisticsDTO> successResponse = statisticsService.getMonthlyStatistics();
        return ResponseEntity.ok().body(new SuccessResponse<>(successResponse));
    }


}
