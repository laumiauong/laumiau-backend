package laumiau.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cliente")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Cliente extends Usuario {

    public Cliente() {}

    public Cliente(Long id, String nome, String email, String senha) {
        super(id, nome, email, senha);
        this.setTipo(TipoUsuario.cliente);
    }

    @Override
    public void exibirPermissoes() {
        System.out.println(" Permissões do CLIENTE: ");
        System.out.println("- Cadastrar usuário");
        System.out.println("- Realizar login");
        System.out.println("- Acessar perfil");
        System.out.println("- Acessar galeria de animais");
        System.out.println("- Acessar formulário de adoção");
        System.out.println("- Acessar página sobre ong");
    }
}