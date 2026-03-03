package laumiau;

public class Tutor extends Usuario {

    private String telefone;

    public Tutor(String senha, String email, String nome, Long id, String telefone) {
        super(senha, email, nome, id);
        this.telefone = telefone;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public void exibirPermissoes() {
        System.out.println(" Permissões do TUTOR: ");
        System.out.println("- Visualizar animais disponíveis");
        System.out.println("- Preencher formulário de interesse");
        System.out.println("- Acompanhar status da adoção");
    }

    @Override
    public String getNome() {
        return "[Tutor] " + super.getNome();
    }
}