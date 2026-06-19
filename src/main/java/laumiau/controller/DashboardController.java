package laumiau.controller;

import laumiau.infra.JPAUtil;
import laumiau.model.Relatorio;
import laumiau.service.RelatorioService;


public class DashboardController {


    public Relatorio obterDadosDashboard() {
        try {
            var em = JPAUtil.getEntityManager();
            RelatorioService rs = new RelatorioService(em);
            Relatorio relatorio = rs.obterRelatorioGeral();
            em.close();
            return relatorio;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}