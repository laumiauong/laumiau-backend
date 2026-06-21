package laumiau.controller;

import laumiau.model.Adocoes;
import laumiau.model.SolicitacaoAdocao;
import laumiau.service.AdocoesService;

import java.util.List;


public class AdocaoController {

    private final AdocoesService adocoesService;

    public AdocaoController(AdocoesService adocoesService) {
        this.adocoesService = adocoesService;
    }


    public String registrarSolicitacao(Long animalId, Long clienteId,
                                       boolean termoAssinado,
                                       String telefone, String cpf,
                                       String dataNascimento, String profissao,
                                       String tipoMoradia, String possuiQuintal,
                                       String tevePetsAntes, String outrosPets,
                                       String motivoAdocao) {
        try {
            adocoesService.registrarAdocao(
                    animalId, clienteId, termoAssinado,
                    telefone, cpf, dataNascimento, profissao,
                    tipoMoradia, possuiQuintal,
                    tevePetsAntes, outrosPets, motivoAdocao
            );
            return null;
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public List<Adocoes> listarPendentes() {
        try {
            return adocoesService.listarPendentes();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }


    public List<Adocoes> listarTodas() {
        try {
            return adocoesService.listarTodos();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }


    public SolicitacaoAdocao buscarDetalhesFormulario(Long adocaoId) {
        try {
            return adocoesService.buscarSolicitacaoPorAdocao(adocaoId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public Adocoes buscarAdocao(Long adocaoId) {
        try {
            return adocoesService.buscarPorId(adocaoId);
        } catch (Exception e) {
            return null;
        }
    }

    public String aprovarAdocao(Long adocaoId) {
        try {
            adocoesService.aprovarAdocao(adocaoId);
            return null;
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }


    public String recusarAdocao(Long adocaoId) {
        try {
            adocoesService.recusarAdocao(adocaoId);
            return null;
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
}