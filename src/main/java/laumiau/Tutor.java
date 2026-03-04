package laumiau;

import java.util.ArrayList;
import java.util.List;

public class Tutor extends Usuario {

    private String telefone;
    private List<Animal> petsAdotados = new ArrayList<>();

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

    public void adicionarPet(Animal animal) {
        this.petsAdotados.add(animal);
    }

    public List<Animal> getPetsAdotados() {
        return petsAdotados;
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