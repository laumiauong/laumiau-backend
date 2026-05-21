package br.com.laumiau.view;

import javax.swing.SwingUtilities;

public class UsuarioLogin {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView telaLogin = new LoginView();
            telaLogin.setVisible(true);
        });
    }
}