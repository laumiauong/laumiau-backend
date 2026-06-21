package laumiau.controller;

import laumiau.model.Relatorio;
import laumiau.service.RelatorioService;


public class DashboardController {

    private final RelatorioService relatorioService;

    public DashboardController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    public Relatorio obterDadosDashboard() {
        try {
            return relatorioService.obterRelatorioGeral();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}