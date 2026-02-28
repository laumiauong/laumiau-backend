package laumiau;


public class Main {
    public static void main(String[] args) {
        Animal b1 = new Animal(
            "Luna",
            "Cachorro",
            "Daschund",
            1,
            Sexo.FEMEA,
            true,
            Porte.PEQUENO);

System.out.println("--- LISTA DE ANIMAIS ----");
        b1.exibirDados();
        System.out.println("----------------------------------------");



    }
}