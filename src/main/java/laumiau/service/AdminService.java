package laumiau.service;

import laumiau.model.Admin;
import laumiau.repository.UsuarioRepository;

public class AdminService {

    private final UsuarioRepository usuarioRepository;

    public AdminService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void cadastrar(Admin admin) {
        if (usuarioRepository.buscarPorEmail(admin.getEmail()) != null) {
            throw new RuntimeException("Email já cadastrado!");
        }
        usuarioRepository.salvar(admin);
        System.out.println("Administrador cadastrado com sucesso!");
    }
}