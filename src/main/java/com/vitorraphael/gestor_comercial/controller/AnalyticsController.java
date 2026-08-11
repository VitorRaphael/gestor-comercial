package com.vitorraphael.gestor_comercial.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vitorraphael.gestor_comercial.dto.AnalyticsDashboardResponse;
import com.vitorraphael.gestor_comercial.security.ExigeGerente;
import com.vitorraphael.gestor_comercial.service.AnalyticsService;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @ExigeGerente
    @GetMapping("/dashboard")
    public ResponseEntity<AnalyticsDashboardResponse> dashboard(@RequestParam(defaultValue = "30") int dias) {
        return ResponseEntity.ok(analyticsService.gerarDashboard(dias));
    }
}
