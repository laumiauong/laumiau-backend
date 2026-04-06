package laumiau.repository;

import jakarta.persistence.*;
import laumiau.model.Tutor;
import java.util.List;

public class TutorRepository {

    private final EntityManager em;
    public TutorRepository(EntityManager em) {
        this.em = em;
    }

    public void salvar(Tutor tutor) {
        try {
            em.getTransaction().begin();
            em.persist(tutor);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao salvar o tutor: " + e.getMessage());
        }
    }
    public Tutor buscarPorId(Long id) {
        try {
            return em.find(Tutor.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar o tutor: " + e.getMessage());
        }
    }
    public List<Tutor> listarTodos() {
        try {
            return em.createNamedQuery("Tutor.findAll", Tutor.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar os tutores: " + e.getMessage());
        }
    }
    public void atualizar (Tutor tutor) {
        try {
            em.getTransaction().begin();
            em.merge(tutor);
            em.getTransaction().commit();
        } catch(Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao atualizar o tutor: " + e.getMessage());
        }
    }
    public void deletar (Long id){
        try {
            em.getTransaction().begin();
            Tutor  tutor = em.find(Tutor.class, id);
            if (tutor != null) em.remove(tutor);
            em.getTransaction().commit();
        } catch(Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao deletar o tutor: " + e.getMessage());
        }
    }
}
