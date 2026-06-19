package laumiau.controller;

import laumiau.infra.JPAUtil;
import laumiau.model.Adocoes;
import laumiau.model.SolicitacaoAdocao;
import laumiau.model.StatusAnimal;
import laumiau.repository.*;
import laumiau.service.AdocoesService;

import java.util.List;


public class AdocaoController {

    private AdocoesService criarService() {
        return new AdocoesService(
                new AdocoesRepository(JPAUtil.getEntityManager()),
                new AnimalRepository(JPAUtil.getEntityManager()),
                new ClienteRepository(JPAUtil.getEntityManager()),
                new SolicitacaoAdocaoRepository(JPAUtil.getEntityManager())
        );
    }


    public String registrarSolicitacao(Long animalId, Long clienteId,
                                       boolean termoAssinado,
                                       String telefone, String cpf,
                                       String dataNascimento, String profissao,
                                       String tipoMoradia, String possuiQuintal,
                                       String tevePetsAntes, String outrosPets,
                                       String motivoAdocao) {
        try {
            criarService().registrarAdocao(
                    animalId, clienteId, termoAssinado,
                    telefone, cpf, dataNascimento, profissao,
                    tipoMoradia, possuiQuintal,
                    tevePetsAntes, outrosPets, motivoAdocao
            );
            return null; // sucesso
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public List<Adocoes> listarPendentes() {
        try {
            var em = JPAUtil.getEntityManager();
            List<Adocoes> lista = em.createQuery(
                            "SELECT a FROM Adocoes a " +
                                    "LEFT JOIN FETCH a.animal " +
                                    "LEFT JOIN FETCH a.cliente " +
                                    "WHERE a.status = laumiau.model.StatusAdocao.PENDENTE " +
                                    "ORDER BY a.idAdocao DESC",
                            Adocoes.class)
                    .setMaxResults(20)
                    .getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<Adocoes> listarTodas() {
        try {
            var em = JPAUtil.getEntityManager();
            List<Adocoes> lista = em.createQuery(
                            "SELECT a FROM Adocoes a " +
                                    "LEFT JOIN FETCH a.animal " +
                                    "LEFT JOIN FETCH a.cliente " +
                                    "ORDER BY a.idAdocao DESC",
                            Adocoes.class)
                    .getResultList();
            em.close();
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }


    public SolicitacaoAdocao buscarDetalhesFormulario(Long adocaoId) {
        try {
            var em = JPAUtil.getEntityManager();
            SolicitacaoAdocao sol = new SolicitacaoAdocaoRepository(em)
                    .buscarPorAdocaoId(adocaoId);
            em.close();
            return sol;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Adocoes buscarAdocao(Long adocaoId) {
        try {
            var em = JPAUtil.getEntityManager();
            Adocoes adocao = em.find(Adocoes.class, adocaoId);
            em.close();
            return adocao;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public String aprovarAdocao(Long adocaoId) {
        try {
            var em = JPAUtil.getEntityManager();
            Adocoes adocao = em.find(Adocoes.class, adocaoId);

            em.getTransaction().begin();
            adocao.aprovar();
            adocao.getAnimal().setStatus(StatusAnimal.ADOTADO);
            em.merge(adocao.getAnimal());
            em.merge(adocao);
            em.getTransaction().commit();
            em.close();

            return null; // sucesso
        } catch (Exception e) {
            return e.getMessage();
        }
    }


    public String recusarAdocao(Long adocaoId) {
        try {
            var em = JPAUtil.getEntityManager();
            Adocoes adocao = em.find(Adocoes.class, adocaoId);

            em.getTransaction().begin();
            adocao.recusar();
            em.merge(adocao);
            em.getTransaction().commit();
            em.close();

            return null; // sucesso
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}