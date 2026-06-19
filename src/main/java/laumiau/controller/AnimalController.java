package laumiau.controller;

import laumiau.infra.JPAUtil;
import laumiau.model.Animal;
import laumiau.model.StatusAnimal;
import laumiau.repository.AnimalRepository;
import laumiau.service.AnimalService;

import java.util.List;


public class AnimalController {

    private AnimalService criarService() {
        return new AnimalService(new AnimalRepository(JPAUtil.getEntityManager()));
    }

    public List<Animal> listarDisponiveis() {
        try {
            var em = JPAUtil.getEntityManager();
            List<Animal> lista = em.createQuery(
                            "FROM Animal a WHERE a.status = :status ORDER BY a.nome",
                            Animal.class)
                    .setParameter("status", StatusAnimal.DISPONIVEL)
                    .getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<Animal> listarTodos() {
        try {
            return criarService().listarTodos();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }


    public String cadastrar(Animal animal) {
        try {
            criarService().cadastrar(animal);
            return null;
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }


    public String atualizar(Animal animal) {
        try {
            criarService().atualizar(animal);
            return null;
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public String remover(Long id) {
        try {
            criarService().remover(id);
            return null;
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public Animal buscarPorId(Long id) {
        try {
            return criarService().buscarPorId(id);
        } catch (Exception e) {
            return null;
        }
    }
}