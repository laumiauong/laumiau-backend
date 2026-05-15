package laumiau;

import laumiau.view.LoginView;

import javax.swing.SwingUtilities;

public class UsuarioLogin {
    //teste

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginView::new);
    }
}