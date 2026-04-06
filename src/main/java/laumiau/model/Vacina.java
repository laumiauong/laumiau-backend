package laumiau.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "vacina")
public class Vacina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

    @Column(nullable = false)
    private String nome;

    @Column(name = "data_aplicacao", nullable = false)
    private LocalDate dataAplicacao;

    @Column(name = "proxima_dose")
    private LocalDate proximaDose;

    public Vacina() {}

    public Vacina(Animal animal, String nome, LocalDate dataAplicacao, LocalDate proximaDose) {
        if (animal == null) throw new IllegalArgumentException("Animal não pode ser nulo!");
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome da vacina é obrigatório!");
        if (dataAplicacao == null) throw new IllegalArgumentException("Data de aplicação é obrigatória!");

        this.animal = animal;
        this.nome = nome;
        this.dataAplicacao = dataAplicacao;
        this.proximaDose = proximaDose;
    }

    public Long getId() { return id; }
    public Animal getAnimal() { return animal; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public LocalDate getDataAplicacao() { return dataAplicacao; }
    public LocalDate getProximaDose() { return proximaDose; }
    public void setProximaDose(LocalDate proximaDose) { this.proximaDose = proximaDose; }

    @Override
    public String toString() {
        return "ID: " + id +
                "\nAnimal: " + animal.getNome() +
                "\nVacina: " + nome +
                "\nAplicada em: " + dataAplicacao +
                "\nPróxima dose: " + (proximaDose != null ? proximaDose : "Não informada") +
                "\n---------------------------------";
    }
}