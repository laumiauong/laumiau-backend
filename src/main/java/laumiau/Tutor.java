package laumiau;


public class Tutor extends Usuario {


    private String telefone;


    public Tutor(Long id, String nome, String email, String senha, String telefone) {
        super(id, nome, email, senha, TipoUsuario.CLIENTE);
        this.telefone = telefone;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String getNome() {
        return "[Tutor] " + super.getNome();
    }
}
