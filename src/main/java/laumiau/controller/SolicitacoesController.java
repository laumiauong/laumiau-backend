package laumiau.controller;

import laumiau.model.Adocoes;
import laumiau.model.SolicitacaoAdocao;
import laumiau.service.AdocoesService;

import java.util.List;


public class SolicitacoesController {

    private final AdocoesService adocoesService;

    public SolicitacoesController(AdocoesService adocoesService) {
        this.adocoesService = adocoesService;
    }

    public List<Adocoes> listarPendentes() {
        try {
            return adocoesService.listarPendentes();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }


    public Adocoes buscarAdocao(Long idAdocao) {
        try {
            return adocoesService.buscarPorId(idAdocao);
        } catch (Exception e) {
            return null;
        }
    }

    public SolicitacaoAdocao buscarFormulario(Long idAdocao) {
        try {
            return adocoesService.buscarSolicitacaoPorAdocao(idAdocao);
        } catch (Exception e) {
            return null;
        }
    }

    public String aprovar(Long idAdocao) {
        try {
            adocoesService.aprovarAdocao(idAdocao);
            return null;
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }


    public String recusar(Long idAdocao) {
        try {
            adocoesService.recusarAdocao(idAdocao);
            return null;
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
}