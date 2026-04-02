package laumiau;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Relatorio {

    private final List<Animal> animais;
    private final List<Adocoes> adocoes;

    public Relatorio(List<Animal> animais, List<Adocoes> adocoes) {
        this.animais = animais;
        this.adocoes = adocoes;
    }

    public int getTotalAnimais() { return animais.size(); }
    public int getTotalAdocoes() { return adocoes.size(); }

    public int getTotalAnimaisAdotados() {
        int total = 0;
        for (Animal animal : animais) {
            if (animal.isAdotado()) total++;
        }
        return total;
    }

    public int getTotalAnimaisDisponiveis() {
        return getTotalAnimais() - getTotalAnimaisAdotados();
    }

    public int getTotalAnimaisVacinados() {
        int total = 0;
        for (Animal animal : animais) {
            if (animal.isVacinado()) total++;
        }
        return total;
    }

    public double getTaxaAdocao() {
        if (animais.isEmpty()) return 0.0;
        return (double) getTotalAnimaisAdotados() / getTotalAnimais() * 100;
    }

    public double getMediaIdadeAnimais() {
        if (animais.isEmpty()) return 0.0;
        int somaIdades = 0;
        for (Animal animal : animais) {
            somaIdades += animal.getIdade();
        }
        return (double) somaIdades / animais.size();
    }

    public Map<String, Integer> getAnimaisPorEspecie() {
        Map<String, Integer> animaisPorEspecie = new HashMap<>();
        for (Animal animal : animais) {
            String especie = animal.getEspecie();
            animaisPorEspecie.put(especie, animaisPorEspecie.getOrDefault(especie, 0) + 1);
        }
        return animaisPorEspecie;
    }

    public void imprimirResumo() {
        System.out.println("\n========== RELATÓRIO DE NEGÓCIO ==========");
        System.out.println("Total de animais cadastrados: " + getTotalAnimais());
        System.out.println("Total de adoções realizadas: " + getTotalAdocoes());
        System.out.println("Animais adotados: " + getTotalAnimaisAdotados());
        System.out.println("Animais disponíveis: " + getTotalAnimaisDisponiveis());
        System.out.println("Animais vacinados: " + getTotalAnimaisVacinados());
        System.out.printf("Taxa de adoção: %.2f%%\n", getTaxaAdocao());
        System.out.printf("Média de idade dos animais: %.2f anos\n", getMediaIdadeAnimais());

        System.out.println("\nAnimais por espécie:");
        for (Map.Entry<String, Integer> entry : getAnimaisPorEspecie().entrySet()) {
            System.out.println("- " + entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("===========================================");
    }
}