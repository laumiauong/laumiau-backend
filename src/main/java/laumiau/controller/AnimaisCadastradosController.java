package laumiau.controller;

import laumiau.model.Animal;
import laumiau.model.SolicitacaoAdocao;
import laumiau.service.AdocoesService;
import laumiau.service.AnimalService;

import java.util.List;

public class AnimaisCadastradosController {

    private final AnimalService   animalService;
    private final AdocoesService  adocoesService;

    public AnimaisCadastradosController(AnimalService animalService,
                                        AdocoesService adocoesService) {
        this.animalService  = animalService;
        this.adocoesService = adocoesService;
    }

    public List<Animal> listarTodos() {
        try {
            return animalService.listarTodos();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }


    public String remover(Long id) {
        try {
            animalService.remover(id);
            return null;
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }


    public String cancelarAdocao(Long animalId) {
        try {
            adocoesService.cancelarAdocao(animalId);
            return null;
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }


    public SolicitacaoAdocao buscarFormularioDoAnimal(Long animalId) {
        try {
            return adocoesService.buscarSolicitacaoPorAnimal(animalId);
        } catch (Exception e) {
            return null;
        }
    }
}