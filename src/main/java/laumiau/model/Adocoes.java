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

    @Enumerated(EnumType.STRING)
    @Column(name = "status_adocao", nullable = false)
    private StatusAdocao status;

    public Adocoes() {}

    public Adocoes(Animal animal, Cliente cliente, boolean termoAssinado) {
            if(animal == null){
                throw new IllegalArgumentException("Animal não pode ser nulo!");
            }

            if(cliente == null){
                throw new IllegalArgumentException("Cliente não pode ser nulo!");
            }

            if(!termoAssinado){
                throw new IllegalStateException("Adoção só pode ocorrer com termo assinado!");
            }
            if(animal.isAdotado()){
                throw new IllegalStateException("Animal já está adotado!");
            }

            this.animal = animal;
            this.cliente = cliente;
            this.dataAdocao = LocalDate.now();
        this.termoAssinado = termoAssinado;
        this.status = StatusAdocao.PENDENTE;
    }

    public Long getIdAdocao() { return idAdocao; }
    public Animal getAnimal() { return animal; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) {
        if(cliente == null){
            throw new IllegalArgumentException("Cliente inválido!");
        }
        this.cliente = cliente;
    }
    public LocalDate getDataAdocao() { return dataAdocao; }

    public boolean isTermoAssinado() { return termoAssinado; }
    public void setTermoAssinado(boolean termoAssinado) {
        if(!termoAssinado){
            throw new IllegalStateException("Não é permitido remover o termo assinado!");
        }
        this.termoAssinado = true;
    }
    public StatusAdocao getStatus() { return status; }

    public void aprovar() {
        if (!this.termoAssinado) throw new IllegalStateException("Termo não assinado!");
        this.status = StatusAdocao.APROVADO;
        this.animal.adotar();
    }

    public void recusar() {
        this.status = StatusAdocao.RECUSADO;
    }
    public String gerarResumo() {

        if(animal == null || cliente == null){
            throw new IllegalStateException("Dados da adoção incompletos!");
        }

        return "\nRegistro de Adoção" +
                "\nID Adoção: " + idAdocao +
                "\nAnimal: " + animal.getNome() +
                "\nAdotante: " + cliente.getNome() +
                "\nData: " + dataAdocao +
                "\nTermo Assinado: " + (termoAssinado ? "Sim" : "Não");
    }
}