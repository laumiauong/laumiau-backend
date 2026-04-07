package laumiau.repository;

import jakarta.persistence.EntityManager;
import laumiau.model.Vacina;
import java.util.List;

public class VacinaRepository {

    private final EntityManager em;

    public VacinaRepository(EntityManager em) {
        this.em = em;
    }

    public void salvar(Vacina vacina) {
        try {
            em.getTransaction().begin();
            em.persist(vacina);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao salvar vacina: " + e.getMessage());
        }
    }

    public Vacina buscarPorId(Long id) {
        try {
            return em.find(Vacina.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar vacina: " + e.getMessage());
        }
    }

    public List<Vacina> listarTodos() {
        try {
            return em.createQuery("FROM Vacina", Vacina.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar vacinas: " + e.getMessage());
        }
    }

    public List<Vacina> listarPorAnimal(Long animalId) {
        try {
            return em.createQuery(
                            "FROM Vacina v WHERE v.animal.id = :animalId", Vacina.class)
                    .setParameter("animalId", animalId)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar vacinas do animal: " + e.getMessage());
        }
    }

    public void atualizar(Vacina vacina) {
        try {
            em.getTransaction().begin();
            em.merge(vacina);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao atualizar vacina: " + e.getMessage());
        }
    }

    public void deletar(Long id) {
        try {
            em.getTransaction().begin();
            Vacina vacina = em.find(Vacina.class, id);
            if (vacina != null) em.remove(vacina);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao deletar vacina: " + e.getMessage());
        }
    }
}