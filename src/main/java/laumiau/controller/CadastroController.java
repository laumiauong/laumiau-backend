package laumiau.controller;

import laumiau.model.Cliente;
import laumiau.model.Usuario;
import laumiau.service.UsuarioService;


public class CadastroController {

    private final UsuarioService usuarioService;

    public CadastroController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public String cadastrar(String nome, String email, String senha, String confirmarSenha) {
        if (campoVazio(nome) || campoVazio(email) || campoVazio(senha) || campoVazio(confirmarSenha))
            return "Preencha todos os campos.";
        if (!email.contains("@") || !email.contains("."))
            return "Informe um e-mail válido.";
        if (senha.length() < 4)
            return "A senha deve ter pelo menos 4 caracteres.";
        if (!senha.equals(confirmarSenha))
            return "As senhas não coincidem.";

        try {
            Usuario existente = usuarioService.buscarPorEmail(email);
            if (existente != null)
                return "Já existe um usuário cadastrado com este e-mail.";

            Cliente cliente = new Cliente(null, nome, email, senha);
            usuarioService.cadastrar(cliente);
            return null;

        } catch (RuntimeException e) {
            return "Erro ao cadastrar usuário: " + e.getMessage();
        }
    }
    private boolean campoVazio(String valor) {
        return valor == null || valor.isBlank();
    }
}