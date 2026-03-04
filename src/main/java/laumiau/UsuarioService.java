package laumiau;

import java.util.ArrayList;
import java.util.List;

public class UsuarioService {

    private List<Usuario> usuarios = new ArrayList<>();

    // create
    public void cadastrar(Usuario usuario) {
        usuarios.add(usuario);
    }

    // read
    public List<Usuario> listar() {
        return usuarios;
    }

    // buscar por id
    public Usuario buscarPorId(Long id) {
        for (Usuario u : usuarios) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        return null;
    }

    // filtro por nome
    public List<Usuario> buscarPorNome(String nome) {
        List<Usuario> encontrados = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (u.getNome().toLowerCase().contains(nome.toLowerCase())) {
                encontrados.add(u);
            }
        }
        return encontrados;
    }

    // filtro por tipo
    public List<Usuario> buscarPorTipo(Class<?> tipoClasse) {
        List<Usuario> encontrados = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (u.getClass().equals(tipoClasse)) {
                encontrados.add(u);
            }
        }
        return encontrados;
    }

    // update
    public boolean atualizar(Long id, String novoNome, String novoEmail) {
        Usuario u = buscarPorId(id);
        if (u != null) {
            u.setNome(novoNome);
            u.setEmail(novoEmail);
            return true;
        }
        return false;
    }

    // delete
    public boolean remover(Long id) {
        Usuario u = buscarPorId(id);
        if (u != null) {
            usuarios.remove(u);
            return true;
        }
        return false;
    }

    // relatorio
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