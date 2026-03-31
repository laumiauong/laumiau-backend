package laumiau.model;

import jakarta.persistence.*;

@Entity                    // diz que essa classe é uma tabela no banco
@Table(name = "animal")    // nome da tabela no banco
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // o banco gera o ID automaticamente
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String especie;

    @Column(nullable = false)
    private String raca;

    @Column(name = "idade_meses", nullable = false)
    private int idade;

    @Enumerated(EnumType.STRING) // salva "MACHO" ou "FEMEA" no banco, não número
    @Column(nullable = false)
    private Sexo sexo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAnimal status = StatusAnimal.disponivel;

    @Column(nullable = false)
    private boolean vacinado = false;

    @Enumerated(EnumType.STRING)
    private Porte porte;

    // JPA exige um construtor vazio
    public Animal() {}

    // seu construtor original mantido
    public Animal(String nome, String especie, String raca, int idade, Sexo sexo, boolean vacinado, Porte porte) {
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        setIdade(idade);
        this.sexo = sexo;
        this.status = StatusAnimal.disponivel;
        this.vacinado = vacinado;
        this.porte = porte;
    }

    // getters e setters — igual ao seu, só o id muda de long para Long
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
            System.out.println("Idade inválida!");
        }
    }

    public Sexo getSexo() { return sexo; }

    public StatusAnimal getStatus() { return status; }
    public void setStatus(StatusAnimal status) { this.status = status; }

    public boolean isVacinado() { return vacinado; }
    public void vacinar() { this.vacinado = true; }

    public Porte getPorte() { return porte; }

    // adotar agora atualiza o status em vez de um boolean separado
    public boolean isAdotado() { return status == StatusAnimal.adotado; }
    public void adotar() {
        if (status != StatusAnimal.adotado) {
            this.status = StatusAnimal.adotado;
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