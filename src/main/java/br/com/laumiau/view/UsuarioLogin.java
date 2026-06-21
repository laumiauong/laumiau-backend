package br.com.laumiau.view;

import jakarta.persistence.EntityManager;
import laumiau.infra.JPAUtil;
import laumiau.repository.UsuarioRepository;
import laumiau.service.UsuarioService;
import javax.swing.SwingUtilities;

public class UsuarioLogin {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            EntityManager em = JPAUtil.getEntityManager();

            UsuarioRepository usuarioRepository = new UsuarioRepository(em);
            UsuarioService usuarioService = new UsuarioService(usuarioRepository);

            LoginView telaLogin = new LoginView(usuarioService);
            telaLogin.setVisible(true);
        });
    }
}