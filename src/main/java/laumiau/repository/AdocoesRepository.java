package laumiau.repository;

import jakarta.persistence.*;
import laumiau.model.Adocoes;
import java.util.List;

public class AdocoesRepository {

    private final EntityManager em;

    public AdocoesRepository(EntityManager em) {
        this.em = em;
    }

    public void salvar (Adocoes adocao) {
        try  {
            em.getTransaction().begin();
            em.persist(adocao);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao salvar adoção: " + e.getMessage());
        }
    }
    public Adocoes buscarPorId(Long id){
        try {
            return em.find(Adocoes.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar adoção: " + e.getMessage());
        }
    }
    public List<Adocoes> listarTodos(){
        try {
            return em.createQuery("from Adocoes", Adocoes.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar adoções: " + e.getMessage());
        }
    }
    public void atualizar(Adocoes adocao) {
        try {
            em.getTransaction().begin();
            em.merge(adocao);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao atualizar adoção: " + e.getMessage());
        }
    }
    public void deletar(Long id){
        try {
            em.getTransaction().begin();
            Adocoes adocao = em.find(Adocoes.class, id);
            if (adocao != null) em.remove(adocao);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao deletar adoção: " + e.getMessage());
        }
    }
}
