package laumiau.repository;

import jakarta.persistence.*;
import laumiau.model.Usuario;
import java.util.List;

public class UsuarioRepository {

    private final EntityManager em;

    public UsuarioRepository(EntityManager em) {
        this.em = em;
    }
    public void salvar(Usuario usuario) {
        try {
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao salvar usuário: " + e.getMessage());
        }
    }
    public Usuario buscarPorId(Long id) {
        try {
            return em.find(Usuario.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar usuário: " + e.getMessage());
        }
    }
    public Usuario buscarPorEmail(String email) {
        try {
            return em.createQuery("FROM Usuario WHERE email = :email", Usuario.class)
                    .setParameter("email", email).getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch  (Exception e) {
            throw new RuntimeException("Erro ao buscar email: " + e.getMessage());
        }
    }
    public List<Usuario> listarUsuarios() {
        try {
            return em.createQuery("FROM Usuario", Usuario.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar usuários: " + e.getMessage());
        }
    }
    public void atualizar(Usuario usuario) {
        try {
            em.getTransaction().begin();
            em.merge(usuario);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao atualizar usuario: " + e.getMessage());
        }
    }
    public void remover(Long id) {
        try {
            em.getTransaction().begin();
            Usuario usuario = em.find(Usuario.class, id);
            if (usuario != null) em.remove(usuario);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new  RuntimeException("Erro ao remover usuario: " + e.getMessage());
        }
    }
}

