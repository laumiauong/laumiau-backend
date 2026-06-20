package laumiau.service;

import laumiau.model.Admin;
import laumiau.model.Cliente;
import laumiau.model.Usuario;
import laumiau.repository.UsuarioRepository;
import java.util.ArrayList;
import java.util.List;

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void cadastrar(Usuario usuario) {
        usuarioRepository.salvar(usuario);
    }

    public List<Usuario> listar() {
        return usuarioRepository.listarUsuarios();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.buscarPorId(id);
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.buscarPorEmail(email);
    }

    public List<Usuario> buscarPorNome(String nome) {
        List<Usuario> encontrados = new ArrayList<>();
        for (Usuario u : usuarioRepository.listarUsuarios()) {
            if (u.getNome().toLowerCase().contains(nome.toLowerCase())) {
                encontrados.add(u);
            }
        }
        return encontrados;
    }

    public List<Usuario> buscarPorTipo(Class<?> tipoClasse) {
        List<Usuario> encontrados = new ArrayList<>();
        for (Usuario u : usuarioRepository.listarUsuarios()) {
            if (u.getClass().equals(tipoClasse)) {
                encontrados.add(u);
            }
        }
        return encontrados;
    }

    public boolean atualizar(Long id, String novoNome, String novoEmail) {
        Usuario u = buscarPorId(id);
        if (u != null) {
            u.setNome(novoNome);
            u.setEmail(novoEmail);
            usuarioRepository.atualizar(u);
            return true;
        }
        return false;
    }

    public boolean remover(Long id) {
        Usuario u = buscarPorId(id);
        if (u != null) {
            usuarioRepository.remover(id);
            return true;
        }
        return false;
    }

    public void relatorioUsuarios() {
        List<Usuario> usuarios = usuarioRepository.listarUsuarios();
        int total = usuarios.size();
        int totalAdmins = 0;
        int totalClientes = 0;

        for (Usuario u : usuarios) {
            if (u instanceof Admin) {
                totalAdmins++;
            } else if (u instanceof Cliente) {
                totalClientes++;
            }
        }

        System.out.println("\n=== RELATÓRIO DA ONG ===");
        System.out.println("Total de usuários: " + total);
        System.out.println("Total de Admins: " + totalAdmins);
        System.out.println("Total de Clientes: " + totalClientes);
    }
}