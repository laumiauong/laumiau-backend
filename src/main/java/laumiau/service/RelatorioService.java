package laumiau.service;

import laumiau.model.Adocoes;
import laumiau.model.Animal;
import laumiau.model.Relatorio;
import jakarta.persistence.*;
import java.util.List;

public class RelatorioService {

    private final EntityManager em;

    public RelatorioService(EntityManager em) {
        this.em = em;
    }

    public void gerarRelatorio() {
        // busca os dados do banco
        List<Animal> animais = em.createQuery("FROM Animal", Animal.class)
                .getResultList();

        List<Adocoes> adocoes = em.createQuery("FROM Adocoes", Adocoes.class)
                .getResultList();

        // passa para o Relatorio que já faz os cálculos
        Relatorio relatorio = new Relatorio(animais, adocoes);
        relatorio.imprimirResumo();
    }
}