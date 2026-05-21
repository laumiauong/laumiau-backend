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

    @ManyToOne
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(name = "data_adocao", nullable = false)
    private LocalDate dataAdocao;

    @Column(name = "termo_assinado", nullable = false)
    private boolean termoAssinado;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_adocao", nullable = false)
    private StatusAdocao status;

    public Adocoes() {}

    public Adocoes(Animal animal, Cliente cliente, boolean termoAssinado) {
        if(animal == null) throw new IllegalArgumentException("Animal não pode ser nulo!");
        if(cliente == null) throw new IllegalArgumentException("Cliente não pode ser nulo!");
        if(!termoAssinado) throw new IllegalStateException("Adoção só pode ocorrer com termo assinado!");

        this.animal = animal;
        this.cliente = cliente;
        this.dataAdocao = LocalDate.now();
        this.termoAssinado = termoAssinado;
        this.status = StatusAdocao.PENDENTE;
    }

    public Long getIdAdocao() { return idAdocao; }
    public Animal getAnimal() { return animal; }
    public Cliente getCliente() { return cliente; }
    public StatusAdocao getStatus() { return status; }

    public void aprovar() {
        if (!this.termoAssinado) throw new IllegalStateException("Termo não assinado!");
        this.status = StatusAdocao.APROVADO;
        if (this.animal != null) {
            this.animal.setStatus(StatusAnimal.ADOTADO);
        }
    }

    public void recusar() {
        this.status = StatusAdocao.RECUSADO;
        if (this.animal != null) {
            this.animal.setStatus(StatusAnimal.DISPONIVEL);
        }
    }

    public String gerarResumo() {
        if(animal == null || cliente == null){
            return "Dados da adoção incompletos!";
        }
        return "\nRegistro de Adoção" +
                "\nID Adoção: " + idAdocao +
                "\nAnimal: " + animal.getNome() +
                "\nAdotante: " + cliente.getNome() +
                "\nData: " + dataAdocao +
                "\nTermo Assinado: " + (termoAssinado ? "Sim" : "Não");
    }
}