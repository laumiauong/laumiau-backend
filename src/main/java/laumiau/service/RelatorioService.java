package laumiau.service;

import laumiau.model.Relatorio;
import laumiau.model.Animal;
import laumiau.model.Adocoes;
import laumiau.repository.AnimalRepository;
import laumiau.repository.AdocoesRepository;
import jakarta.persistence.EntityManager;
import java.util.List;

public class RelatorioService {
    private final AnimalRepository animalRepository;
    private final AdocoesRepository adocoesRepository;
    private final EntityManager em; // Adicionado para usar o que vem do construtor

    public RelatorioService(EntityManager em) {
        this.em = em;
        this.animalRepository = new AnimalRepository(em);
        this.adocoesRepository = new AdocoesRepository(em);
    }

    public Relatorio obterRelatorioGeral() {
        try {
            // Busca os dados do banco usando o EntityManager local
            List<Animal> animais = em.createQuery("FROM Animal", Animal.class).getResultList();
            List<Adocoes> adocoes = em.createQuery("FROM Adocoes", Adocoes.class).getResultList();

            // Retorna o objeto Relatorio para a Dashboard do NetBeans
            return new Relatorio(animais, adocoes);
        } catch (Exception e) {
            System.err.println("Erro ao buscar dados: " + e.getMessage());
            return null;
        }
    }
}