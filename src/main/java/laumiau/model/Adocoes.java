package laumiau.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "adocoes")
public class Adocoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idAdocao;

    @ManyToOne                          // muitas adoções podem ter um animal
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

    @ManyToOne                          // muitas adoções podem ter um cliente
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(name = "data_adocao", nullable = false)
    private LocalDate dataAdocao;

    @Column(name = "termo_assinado", nullable = false)
    private boolean termoAssinado;

    public Adocoes() {}

    public Adocoes(Animal animal, Cliente cliente, boolean termoAssinado) {
        this.animal = animal;
        this.cliente = cliente;
        this.dataAdocao = LocalDate.now();
        this.termoAssinado = termoAssinado;
    }

    public Long getIdAdocao() { return idAdocao; }

    public Animal getAnimal() { return animal; }
    public void setAnimal(Animal animal) { this.animal = animal; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public LocalDate getDataAdocao() { return dataAdocao; }

    public boolean isTermoAssinado() { return termoAssinado; }
    public void setTermoAssinado(boolean termoAssinado) { this.termoAssinado = termoAssinado; }

    public String gerarResumo() {
        return "\nRegistro de Adoção" +
                "\nID Adoção: " + idAdocao +
                "\nAnimal: " + animal.getNome() +
                "\nAdotante: " + cliente.getNome() +
                "\nData: " + dataAdocao +
                "\nTermo Assinado: " + (termoAssinado ? "Sim" : "Não");
    }
}