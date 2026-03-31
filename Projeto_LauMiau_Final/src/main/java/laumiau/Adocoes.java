package laumiau;

import java.time.LocalDate;

public class Adocoes {

    private long idAdocao;
    private Animal animal;
    private String nomeAdotante;
    private String telefoneAdotante;
    private LocalDate dataAdocao;
    private boolean termoAssinado;

    public Adocoes(long idAdocao, Animal animal, String nomeAdotante,
                   String telefoneAdotante, boolean termoAssinado) {

        this.idAdocao = idAdocao;
        this.animal = animal;
        this.nomeAdotante = nomeAdotante;
        this.telefoneAdotante = telefoneAdotante;
        this.dataAdocao = LocalDate.now();
        this.termoAssinado = termoAssinado;
    }

    public long getIdAdocao() {
        return idAdocao;
    }

    public Animal getAnimal() {
        return animal;
    }

    public String getNomeAdotante() {
        return nomeAdotante;
    }

    public String getTelefoneAdotante() {
        return telefoneAdotante;
    }

    public LocalDate getDataAdocao() {
        return dataAdocao;
    }

    public boolean isTermoAssinado() {
        return termoAssinado;
    }

    public void setTelefoneAdotante(String telefoneAdotante) {
        this.telefoneAdotante = telefoneAdotante;
    }

    public void setTermoAssinado(boolean termoAssinado) {
        this.termoAssinado = termoAssinado;
    }

    public String gerarResumo() {
        return "\nRegistro de Adoção" +
                "\nID Adoção: " + idAdocao +
                "\nAnimal: " + animal.getNome() +
                "\nEspécie: " + animal.getClass().getSimpleName() +
                "\nAdotante: " + nomeAdotante +
                "\nTelefone: " + telefoneAdotante +
                "\nData: " + dataAdocao +
                "\nTermo Assinado: " + (termoAssinado ? "Sim" : "Não");
    }
}