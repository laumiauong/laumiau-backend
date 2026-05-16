package laumiau.model;

import jakarta.persistence.*;

@Entity
@Table(name = "admin")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Admin extends Usuario {

    public Admin() {}

    public Admin(Long id, String nome, String email, String senha) {
        super(id, nome, email, senha);
        this.setTipo(TipoUsuario.admin);
    }

    public Admin(Long id, String nome, String email, String senha, Endereco endereco) {
        super(id, nome, email, senha, endereco);
        this.setTipo(TipoUsuario.admin);
    }

    @Override
    public void exibirPermissoes() {
        System.out.println("Permissões do ADMIN:");
        System.out.println("- Cadastrar pets");
        System.out.println("- Editar pets");
        System.out.println("- Gerenciar pets");
        System.out.println("- Gerenciar usuários");
    }
}