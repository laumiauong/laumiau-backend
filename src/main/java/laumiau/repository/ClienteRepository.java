package laumiau.repository;

import jakarta.persistence.*;
import laumiau.model.Cliente;
import java.util.List;

public class ClienteRepository {

    private final EntityManager em;

    public ClienteRepository(EntityManager em) {
        this.em = em;
    }
    public void salvar (Cliente cliente) {
        try {
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao salvar cliente: " + e.getMessage());
        }
    }
    public Cliente buscarPorId(Long id) {
        try {
            return em.find(Cliente.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar cliente: " + e.getMessage());
        }
    }
    public List<Cliente> listarTodos() {
        try {
            return em.createQuery("FROM Cliente", Cliente.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar clientes: " + e.getMessage());
        }
    }
    public void atualizar(Cliente cliente) {
        try {
            em.getTransaction().begin();
            em.merge(cliente);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao atualizar cliente: " + e.getMessage());
        }
    }
    public void remover(Long id) {
        try {
            em.getTransaction().begin();
            Cliente cliente = em.find(Cliente.class, id);
            if (cliente != null) em.remove(cliente);
            em.getTransaction().commit();
    } catch (Exception e) {
        em.getTransaction().rollback();
        throw new RuntimeException("Erro ao remover cliente: " + e.getMessage());
        }
    }
}
