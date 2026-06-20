package laumiau.controller;

import laumiau.model.Cliente;
import laumiau.model.TipoUsuario;
import laumiau.model.Usuario;
import laumiau.service.UsuarioService;


public class LoginController {

    public enum ResultadoLogin {
        SUCESSO_CLIENTE,
        SUCESSO_ADMIN,
        CREDENCIAIS_INVALIDAS,
        USUARIO_NAO_CLIENTE,
        ADMIN_NA_TELA_ERRADA,
        ERRO
    }

    private final UsuarioService usuarioService;
    private Usuario usuarioLogado;

    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public ResultadoLogin autenticarCliente(String email, String senha) {
        if (email == null || email.isBlank() || senha == null || senha.isBlank())
            return ResultadoLogin.CREDENCIAIS_INVALIDAS;
        try {
            Usuario usuario = usuarioService.buscarPorEmail(email);
            if (usuario == null || !usuario.autenticar(senha))
                return ResultadoLogin.CREDENCIAIS_INVALIDAS;
            if (usuario.getTipo() == TipoUsuario.admin)
                return ResultadoLogin.ADMIN_NA_TELA_ERRADA;
            if (!(usuario instanceof Cliente))
                return ResultadoLogin.USUARIO_NAO_CLIENTE;

            this.usuarioLogado = usuario;
            return ResultadoLogin.SUCESSO_CLIENTE;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultadoLogin.ERRO;
        }
    }

    public ResultadoLogin autenticarAdmin(String email, String senha) {
        if (email == null || email.isBlank() || senha == null || senha.isBlank())
            return ResultadoLogin.CREDENCIAIS_INVALIDAS;
        try {
            Usuario usuario = usuarioService.buscarPorEmail(email);
            if (usuario == null || !usuario.autenticar(senha))
                return ResultadoLogin.CREDENCIAIS_INVALIDAS;
            if (usuario.getTipo() != TipoUsuario.admin)
                return ResultadoLogin.CREDENCIAIS_INVALIDAS;

            this.usuarioLogado = usuario;
            return ResultadoLogin.SUCESSO_ADMIN;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultadoLogin.ERRO;
        }
    }

    public Cliente getClienteLogado() {
        return (usuarioLogado instanceof Cliente) ? (Cliente) usuarioLogado : null;
    }
}