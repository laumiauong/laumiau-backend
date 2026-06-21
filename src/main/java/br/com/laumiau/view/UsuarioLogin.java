package br.com.laumiau.view;

import jakarta.persistence.EntityManager;
import laumiau.controller.LoginController;
import laumiau.infra.JPAUtil;
import laumiau.repository.AnimalRepository;
import laumiau.repository.SolicitacaoAdocaoRepository;
import laumiau.repository.UsuarioRepository;
import laumiau.service.AnimalService;
import laumiau.service.UsuarioService;
import javax.swing.SwingUtilities;

public class UsuarioLogin {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            EntityManager em = JPAUtil.getEntityManager();

            UsuarioRepository usuarioRepository = new UsuarioRepository(em);
            UsuarioService usuarioService = new UsuarioService(usuarioRepository);
            AnimalService animalService = new AnimalService(
                    new AnimalRepository(em),
                    new SolicitacaoAdocaoRepository(em)
            );
            LoginController loginController = new LoginController(usuarioService);

            LoginView telaLogin = new LoginView(usuarioService, loginController, animalService);
            telaLogin.setVisible(true);
        });
    }
}
