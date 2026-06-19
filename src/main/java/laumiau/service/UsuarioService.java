package laumiau.service;

import laumiau.model.Admin;
import laumiau.model.Cliente;
import laumiau.model.Usuario;
import laumiau.repository.UsuarioRepository;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public void cadastrar(Usuario usuario) {
        repository.salvar(usuario);
    }

    public List<Usuario> listar() {
        return repository.listarUsuarios();
    }

    public Usuario buscarPorId(Long id) {
        return repository.buscarPorId(id);
    }

    public Usuario buscarPorEmail(String email) {
        return repository.buscarPorEmail(email);
    }

    public List<Usuario> buscarPorNome(String nome) {
        return repository.listarUsuarios().stream()
                .filter(u -> u.getNome().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Usuario> buscarPorTipo(Class<?> tipoClasse) {
        return repository.listarUsuarios().stream()
                .filter(u -> u.getClass().equals(tipoClasse))
                .collect(Collectors.toList());
    }

    public boolean atualizar(Long id, String novoNome, String novoEmail) {
        Usuario usuario = repository.buscarPorId(id);
        if (usuario == null) return false;

        usuario.setNome(novoNome);
        usuario.setEmail(novoEmail);
        repository.atualizar(usuario);
        return true;
    }

    public boolean remover(Long id) {
        Usuario usuario = repository.buscarPorId(id);
        if (usuario == null) return false;

        repository.remover(id);
        return true;
    }

    public void relatorioUsuarios() {
        List<Usuario> usuarios = repository.listarUsuarios();
        long totalAdmins   = usuarios.stream().filter(u -> u instanceof Admin).count();
        long totalClientes = usuarios.stream().filter(u -> u instanceof Cliente).count();

        System.out.println("\n=== RELATÓRIO DA ONG ===");
        System.out.println("Total de usuários: " + usuarios.size());
        System.out.println("Total de Admins: "   + totalAdmins);
        System.out.println("Total de Clientes: " + totalClientes);
    }
}