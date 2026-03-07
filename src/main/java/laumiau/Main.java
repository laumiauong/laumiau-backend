package laumiau;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Animal> animais = new ArrayList<>();
        List<Adocoes> adocoes = new ArrayList<>();

        Animal thor = new Animal("Thor", "Cachorro", "Vira-lata", 3, Sexo.MACHO, true, Porte.MEDIO);
        Animal luna = new Animal("Luna", "Gato", "Siamês", 2, Sexo.FEMEA, false, Porte.PEQUENO);
        Animal mel = new Animal("Mel", "Cachorro", "Poodle", 5, Sexo.FEMEA, true, Porte.PEQUENO);

        animais.add(thor);
        animais.add(luna);
        animais.add(mel);

        Tutor tutor1 = new Tutor(1L, "Carla", "carla@email.com", "123", "11999990000");
        Tutor tutor2 = new Tutor(2L, "João", "joao@email.com", "123", "11988887777");

        Adocoes adocao1 = tutor1.preencherFormulario(1L, thor, true);
        thor.adotar();
        tutor1.adicionarPet(thor);
        adocoes.add(adocao1);

        Adocoes adocao2 = tutor2.preencherFormulario(2L, luna, true);
        luna.adotar();
        tutor2.adicionarPet(luna);
        adocoes.add(adocao2);

        Relatorio relatorio = new Relatorio(animais, adocoes);
        relatorio.imprimirResumo();
    }
}