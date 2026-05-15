package laumiau.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tutor")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Tutor extends Usuario {

    @Column(unique = true, nullable = false)
    private String telefone;

    @ManyToMany
    @JoinTable(
            name = "tutor_animal",
            joinColumns = @JoinColumn(name = "tutor_id"),
            inverseJoinColumns = @JoinColumn(name = "animal_id")
    )
    private List<Animal> animaisInteresse = new ArrayList<>();
    @OneToMany
    @JoinColumn(name = "cliente_id")
    private List<Animal> petsAdotados = new ArrayList<>();

    public Tutor() {}

    public Tutor(Long id, String nome, String email, String senha, Endereco endereco, String telefone) {
        super(id, nome, email, senha, endereco);
        this.telefone = telefone;
        this.setTipo(TipoUsuario.tutor);
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<Animal> getAnimaisInteresse() { return animaisInteresse; }
    public void adicionarInteresse(Animal animal) { this.animaisInteresse.add(animal); }
    public void adicionarPet(Animal animal) {
        this.petsAdotados.add(animal);
    }

    public List<Animal> getPetsAdotados() {
        return petsAdotados;
    }

    public Adocoes preencherFormulario(Animal animal, boolean termoAssinado) {
        Cliente cliente = new Cliente(
                this.getId(),
                this.getNome(),
                this.getEmail(),
                this.getSenha(),
                this.getEndereco()
        );

        return new Adocoes(animal, cliente, termoAssinado);
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