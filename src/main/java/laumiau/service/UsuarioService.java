package laumiau.service;

import laumiau.model.Admin;
import laumiau.model.Cliente;
import laumiau.model.Usuario;
import java.util.ArrayList;
import java.util.List;

public class UsuarioService {

    private List<Usuario> usuarios = new ArrayList<>();


    public void cadastrar(Usuario usuario) {
        usuarios.add(usuario);
    }


    public List<Usuario> listar() {
        return usuarios;
    }


    public Usuario buscarPorId(Long id) {
        for (Usuario u : usuarios) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        return null;
    }


    public List<Usuario> buscarPorNome(String nome) {
        List<Usuario> encontrados = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (u.getNome().toLowerCase().contains(nome.toLowerCase())) {
                encontrados.add(u);
            }
        }
        return encontrados;
    }


    public List<Usuario> buscarPorTipo(Class<?> tipoClasse) {
        List<Usuario> encontrados = new ArrayList<>();
        for (Usuario u : usuarios) {
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
            return true;
        }
        return false;
    }


    public boolean remover(Long id) {
        Usuario u = buscarPorId(id);
        if (u != null) {
            usuarios.remove(u);
            return true;
        }
        return false;
    }


    public void relatorioUsuarios() {
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