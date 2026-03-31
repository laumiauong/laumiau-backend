package laumiau.model;

import java.util.ArrayList;
import java.util.List;

public class Tutor extends Usuario {

    private String telefone;
    private List<Animal> petsAdotados = new ArrayList<>();

    public Tutor(Long id, String nome, String email, String senha, String telefone) {
        super(id, nome, email, senha);
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

    public Adocoes preencherFormulario(long idAdocao, Animal animal, boolean termoAssinado) {
        return new Adocoes(idAdocao, animal, this.getNome(), this.getTelefone(), termoAssinado);
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