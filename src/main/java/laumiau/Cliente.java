package laumiau;

public class Cliente extends Usuario{
    public Cliente(String senha, String email, String nome, Long id) {
        super(senha, email, nome, id);
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
}
