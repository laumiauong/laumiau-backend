package laumiau.controller;

import laumiau.model.Animal;
import laumiau.service.AnimalService;

import java.util.List;


public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    public List<Animal> listarTodos() {
        try {
            return animalService.listarTodos();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<Animal> listarDisponiveis() {
        try {
            return animalService.listarDisponiveis();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public Animal buscarPorId(Long id) {
        try {
            return animalService.buscarPorId(id);
        } catch (Exception e) {
            return null;
        }
    }


    public String cadastrar(Animal animal) {
        try {
            animalService.cadastrar(animal);
            return null;
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }


    public String atualizar(Animal animal) {
        try {
            animalService.atualizar(animal);
            return null;
        } catch (RuntimeException e) {
            return e.getMessage();
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
}