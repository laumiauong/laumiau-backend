package laumiau.model;

import jakarta.persistence.*;

@Entity
@Table(name = "animal")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String especie;

    @Column(nullable = false)
    private String raca;

    @Column(name = "idade_meses", nullable = false)
    private int idade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sexo sexo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAnimal status = StatusAnimal.DISPONIVEL;

    @Column(nullable = false)
    private boolean vacinado = false;

    @Enumerated(EnumType.STRING)
    private Porte porte;

    // JPA exige construtor vazio
    public Animal() {}

    public Animal(String nome, String especie, String raca, int idade, Sexo sexo, boolean vacinado, Porte porte) {
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        setIdade(idade);
        this.sexo = sexo;
        this.status = StatusAnimal.DISPONIVEL;
        this.vacinado = vacinado;
        this.porte = porte;
    }

    public Long getId() { return id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public String getRaca() { return raca; }
    public void setRaca(String raca) { this.raca = raca; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) {
        if (idade >= 0) {
            this.idade = idade;
        } else {
            throw new RuntimeException("Idade inválida! A idade não pode ser negativa.");
        }
    }

    public Sexo getSexo() { return sexo; }

    public StatusAnimal getStatus() { return status; }
    public void setStatus(StatusAnimal status) { this.status = status; }

    public boolean isVacinado() { return vacinado; }
    public void vacinar() { this.vacinado = true; }

    public Porte getPorte() { return porte; }

    public boolean isAdotado() {
        return status == StatusAnimal.ADOTADO;
    }

    public void adotar() {
        if (status != StatusAnimal.ADOTADO) {
            this.status = StatusAnimal.ADOTADO;
            System.out.println("Animal adotado!");
        } else {
            System.out.println("Esse animal já foi adotado!");
        }
    }

    @Override
    public String toString() {
        return "ID: " + id +
                "\nNome: " + nome +
                "\nEspécie: " + especie +
                "\nRaça: " + raca +
                "\nIdade: " + idade + " meses" +
                "\nSexo: " + sexo +
                "\nStatus: " + status +
                "\nVacinado: " + (vacinado ? "Sim" : "Não") +
                "\nPorte: " + porte +
                "\n---------------------------------";
    }
}