package laumiau.controller;

import laumiau.model.Usuario;
import laumiau.service.UsuarioService;

import java.util.List;

public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    public Usuario autenticar(String email, String senha) {
        Usuario usuario = service.buscarPorEmail(email);
        if (usuario == null) return null;
        if (!usuario.autenticar(senha)) return null;
        return usuario;
    }

    public void cadastrar(Usuario usuario) {
        service.cadastrar(usuario);
    }

    public Usuario buscarPorId(Long id) {
        return service.buscarPorId(id);
    }

    public Usuario buscarPorEmail(String email) {
        return service.buscarPorEmail(email);
    }

    public List<Usuario> listar() {
        return service.listar();
    }

    public List<Usuario> buscarPorNome(String nome) {
        return service.buscarPorNome(nome);
    }

    public List<Usuario> buscarPorTipo(Class<?> tipoClasse) {
        return service.buscarPorTipo(tipoClasse);
    }

    public boolean atualizar(Long id, String novoNome, String novoEmail) {
        return service.atualizar(id, novoNome, novoEmail);
    }

    public boolean remover(Long id) {
        return service.remover(id);
    }
}