package laumiau;

public class Admin extends Usuario {
    public Admin(String senha, String email, String nome, Long id) {
        super(senha, email, nome, id);
    }
    @Override
    public void exibirPermissoes() {
        System.out.println(" Permissões do ADMIN: ");
        System.out.println("- Cadastrar pets");
        System.out.println("- Editar pets");
        System.out.println("- Gerenciar pets");
        System.out.println("- Gerenciar usuários");
    }
}

