package laumiau.repository;

import jakarta.persistence.*;
import laumiau.model.Animal;
import java.util.List;

public class AnimalRepository {

    private final EntityManager em;

    public AnimalRepository(EntityManager em) {
        this.em = em;
    }

    // create
    public void salvar(Animal animal) {
        try {
            em.getTransaction().begin();
            em.persist(animal);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao salvar animal: " + e.getMessage());
        }
    }


    public Animal buscarPorId(Long id) {
        try {
            return em.find(Animal.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar animal: " + e.getMessage());
        }
    }


    public List<Animal> listarTodos() {
        try {
            return em.createQuery("FROM Animal", Animal.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar animais: " + e.getMessage());
        }
    }


    public void atualizar(Animal animal) {
        try {
            em.getTransaction().begin();
            em.merge(animal);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao atualizar animal: " + e.getMessage());
        }
    }

    // delete
    public void deletar(Long id) {
        try {
            em.getTransaction().begin();
            Animal animal = em.find(Animal.class, id);
            if (animal != null) em.remove(animal);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao deletar animal: " + e.getMessage());
        }
    }
}