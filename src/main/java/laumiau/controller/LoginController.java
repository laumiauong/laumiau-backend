package laumiau.controller;

import jakarta.persistence.EntityManager;
import laumiau.infra.JPAUtil;
import laumiau.model.Cliente;
import laumiau.model.TipoUsuario;
import laumiau.model.Usuario;
import laumiau.repository.UsuarioRepository;


public class LoginController {

    public enum ResultadoLogin {
        SUCESSO_CLIENTE,
        SUCESSO_ADMIN,
        CREDENCIAIS_INVALIDAS,
        USUARIO_NAO_CLIENTE,
        ADMIN_NA_TELA_ERRADA,
        ERRO
    }

    private Usuario usuarioLogado;


    public ResultadoLogin autenticarCliente(String email, String senha) {
        if (email == null || email.isBlank() || senha == null || senha.isBlank()) {
            return ResultadoLogin.CREDENCIAIS_INVALIDAS;
        }
        try {
            EntityManager em = JPAUtil.getEntityManager();
            UsuarioRepository repo = new UsuarioRepository(em);
            Usuario usuario = repo.buscarPorEmail(email);
            em.close();

            if (usuario == null || !usuario.autenticar(senha)) {
                return ResultadoLogin.CREDENCIAIS_INVALIDAS;
            }
            if (usuario.getTipo() == TipoUsuario.admin) {
                return ResultadoLogin.ADMIN_NA_TELA_ERRADA;
            }
            if (!(usuario instanceof Cliente)) {
                return ResultadoLogin.USUARIO_NAO_CLIENTE;
            }

            this.usuarioLogado = usuario;
            return ResultadoLogin.SUCESSO_CLIENTE;

        } catch (Exception e) {
            e.printStackTrace();
            return ResultadoLogin.ERRO;
        }
    }


    public ResultadoLogin autenticarAdmin(String email, String senha) {
        if (email == null || email.isBlank() || senha == null || senha.isBlank()) {
            return ResultadoLogin.CREDENCIAIS_INVALIDAS;
        }
        try {
            EntityManager em = JPAUtil.getEntityManager();
            Usuario usuario = em.createQuery(
                            "SELECT u FROM Usuario u WHERE u.email = :email AND u.senha = :senha AND u.tipo = :tipo",
                            Usuario.class)
                    .setParameter("email", email)
                    .setParameter("senha", senha)
                    .setParameter("tipo", TipoUsuario.admin)
                    .getSingleResult();
            em.close();

            this.usuarioLogado = usuario;
            return ResultadoLogin.SUCESSO_ADMIN;

        } catch (jakarta.persistence.NoResultException e) {
            return ResultadoLogin.CREDENCIAIS_INVALIDAS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultadoLogin.ERRO;
        }
    }


    public Cliente getClienteLogado() {
        if (usuarioLogado instanceof Cliente) {
            return (Cliente) usuarioLogado;
        }
        return null;
    }
}