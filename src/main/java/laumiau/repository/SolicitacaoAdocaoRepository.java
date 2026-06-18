package laumiau.repository;

import jakarta.persistence.EntityManager;
import laumiau.model.SolicitacaoAdocao;
import java.util.List;

public class SolicitacaoAdocaoRepository {

    private final EntityManager em;

    public SolicitacaoAdocaoRepository(EntityManager em) {
        this.em = em;
    }

    public void salvar(SolicitacaoAdocao s) {
        try {
            em.getTransaction().begin();
            em.persist(s);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao salvar solicitação: " + e.getMessage());
        }
    }

    public SolicitacaoAdocao buscarPorAdocaoId(Long adocaoId) {
        try {
            return em.createQuery(
                            "FROM SolicitacaoAdocao s WHERE s.adocao.idAdocao = :id",
                            SolicitacaoAdocao.class)
                    .setParameter("id", adocaoId)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    // NOVO MÉTODO: Busca o formulário diretamente pelo ID do pet
    public SolicitacaoAdocao buscarPorAnimalId(Long animalId) {
        try {
            return em.createQuery(
                            "FROM SolicitacaoAdocao s WHERE s.adocao.animal.id = :animalId ORDER BY s.id DESC",
                            SolicitacaoAdocao.class)
                    .setParameter("animalId", animalId)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<SolicitacaoAdocao> listarTodos() {
        return em.createQuery(
                        "FROM SolicitacaoAdocao s ORDER BY s.dataEnvio DESC",
                        SolicitacaoAdocao.class)
                .getResultList();
    }
}