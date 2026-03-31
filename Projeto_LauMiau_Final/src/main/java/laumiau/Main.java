package laumiau;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in).useLocale(Locale.US);

    public static void main(String[] args) {
        List<Animal> animais = new ArrayList<>();
        List<Adocoes> adocoes = new ArrayList<>();
        long proximoIdAdocao = 1;

        boolean executando = true;
        while (executando) {
            exibirMenuPrincipal();
            int opcao = lerInt("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    cadastrarAnimal(animais);
                    break;
                case 2:
                    listarAnimais(animais);
                    break;
                case 3:
                    atualizarAnimal(animais);
                    break;
                case 4:
                    removerAnimal(animais);
                    break;
                case 5:
                    buscarAnimais(animais);
                    break;
                case 6:
                    proximoIdAdocao = registrarAdocao(animais, adocoes, proximoIdAdocao);
                    break;
                case 7:
                    listarAdocoes(adocoes);
                    break;
                case 8:
                    gerarRelatorio(animais, adocoes);
                    break;
                case 0:
                    executando = false;
                    System.out.println("Encerrando sistema... Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n========== LAUMIAU - SISTEMA DA ONG ==========");
        System.out.println("1. Cadastrar animal");
        System.out.println("2. Listar animais");
        System.out.println("3. Atualizar animal");
        System.out.println("4. Remover animal");
        System.out.println("5. Buscar animais (filtros)");
        System.out.println("6. Registrar adoção");
        System.out.println("7. Listar adoções");
        System.out.println("8. Relatório de negócio");
        System.out.println("0. Sair");
        System.out.println("==============================================");
    }

    private static void cadastrarAnimal(List<Animal> animais) {
        System.out.println("\n--- Cadastro de Animal ---");
        String nome = lerTexto("Nome: ");
        String especie = lerTexto("Espécie: ");
        String raca = lerTexto("Raça: ");
        int idade = lerInt("Idade: ");
        Sexo sexo = lerSexo();
        Porte porte = lerPorte();

        Animal animal = new Animal(nome, especie, raca, idade, sexo, porte);

        if (lerBoolean("Deseja registrar vacinas agora? (s/n): ")) {
            do {
                String vacina = lerTexto("Nome da vacina: ");
                animal.adicionarVacina(vacina);
            } while (lerBoolean("Adicionar outra vacina? (s/n): "));
        }

        animais.add(animal);
        System.out.println("Animal cadastrado com sucesso! ID: " + animal.getId());
    }

    private static void listarAnimais(List<Animal> animais) {
        System.out.println("\n--- Lista de Animais ---");
        if (animais.isEmpty()) {
            System.out.println("Nenhum animal cadastrado.");
            return;
        }
        for (Animal animal : animais) {
            System.out.println(animal);
        }
    }

    private static void atualizarAnimal(List<Animal> animais) {
        System.out.println("\n--- Atualizar Animal ---");
        if (animais.isEmpty()) {
            System.out.println("Não há animais para atualizar.");
            return;
        }

        long id = lerLong("Informe o ID do animal: ");
        Animal animal = buscarAnimalPorId(animais, id);

        if (animal == null) {
            System.out.println("Animal não encontrado.");
            return;
        }

        System.out.println("Animal encontrado: " + animal.getNome());

        String novoNome = lerTextoOpcional("Novo nome (Enter para manter): ");
        if (!novoNome.isEmpty()) animal.setNome(novoNome);

        String novaEspecie = lerTextoOpcional("Nova espécie (Enter para manter): ");
        if (!novaEspecie.isEmpty()) animal.setEspecie(novaEspecie);

        String novaRaca = lerTextoOpcional("Nova raça (Enter para manter): ");
        if (!novaRaca.isEmpty()) animal.setRaca(novaRaca);

        String idadeTexto = lerTextoOpcional("Nova idade (Enter para manter): ");
        if (!idadeTexto.isEmpty()) {
            Integer novaIdade = tentarConverterParaInt(idadeTexto);
            if (novaIdade != null && novaIdade >= 0) animal.setIdade(novaIdade);
        }

        System.out.println("Vacinas atuais: " + (animal.getVacinas().isEmpty() ? "Nenhuma" : animal.getVacinas()));
        if (lerBoolean("Deseja adicionar uma nova vacina? (s/n): ")) {
            String novaVacina = lerTexto("Nome da vacina aplicada: ");
            animal.adicionarVacina(novaVacina);
        }

        System.out.println("Animal atualizado com sucesso.");
    }

    private static void removerAnimal(List<Animal> animais) {
        System.out.println("\n--- Remover Animal ---");
        long id = lerLong("Informe o ID do animal: ");
        Animal animal = buscarAnimalPorId(animais, id);

        if (animal != null && lerBoolean("Confirma remoção de '" + animal.getNome() + "'? (s/n): ")) {
            animais.remove(animal);
            System.out.println("Animal removido.");
        }
    }

    private static void buscarAnimais(List<Animal> animais) {
        System.out.println("\n--- Busca de Animais ---");
        if (animais.isEmpty()) return;

        System.out.println("1. Nome | 2. Espécie | 3. Porte | 4. Disponibilidade");
        int opcao = lerInt("Escolha o filtro: ");
        List<Animal> resultados = new ArrayList<>();

        switch (opcao) {
            case 1 -> {
                String nome = lerTexto("Digite o nome: ").toLowerCase();
                for (Animal a : animais) if (a.getNome().toLowerCase().contains(nome)) resultados.add(a);
            }
            case 2 -> {
                String esp = lerTexto("Digite a espécie: ").toLowerCase();
                for (Animal a : animais) if (a.getEspecie().toLowerCase().contains(esp)) resultados.add(a);
            }
            case 3 -> {
                Porte p = lerPorte();
                for (Animal a : animais) if (a.getPorte() == p) resultados.add(a);
            }
            case 4 -> {
                boolean disp = lerBoolean("Apenas disponíveis? (s/n): ");
                for (Animal a : animais) if (a.isAdotado() != disp) resultados.add(a);
            }
        }

        for (Animal a : resultados) System.out.println(a);
    }

    private static long registrarAdocao(List<Animal> animais, List<Adocoes> adocoes, long proximoIdAdocao) {
        System.out.println("\n--- Registrar Adoção ---");
        long idAnimal = lerLong("Informe o ID do animal: ");
        Animal animal = buscarAnimalPorId(animais, idAnimal);

        if (animal != null && !animal.isAdotado()) {
            String nomeA = lerTexto("Nome do adotante: ");
            String telA = lerTexto("Telefone: ");
            boolean termo = lerBoolean("Termo assinado? (s/n): ");

            Adocoes nova = new Adocoes(proximoIdAdocao, animal, nomeA, telA, termo);
            animal.adotar();
            adocoes.add(nova);
            System.out.println("Adoção registrada!");
            return proximoIdAdocao + 1;
        }
        System.out.println("Animal indisponível.");
        return proximoIdAdocao;
    }

    private static void listarAdocoes(List<Adocoes> adocoes) {
        for (Adocoes a : adocoes) System.out.println(a.gerarResumo());
    }

    private static void gerarRelatorio(List<Animal> animais, List<Adocoes> adocoes) {
        new Relatorio(animais, adocoes).imprimirResumo();
    }

    private static Animal buscarAnimalPorId(List<Animal> animais, long id) {
        for (Animal a : animais) if (a.getId() == id) return a;
        return null;
    }

    private static String lerTexto(String m) {
        System.out.print(m);
        return scanner.nextLine().trim();
    }

    private static String lerTextoOpcional(String m) {
        System.out.print(m);
        return scanner.nextLine().trim();
    }

    private static int lerInt(String m) {
        try {
            System.out.print(m);
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) { return -1; }
    }

    private static long lerLong(String m) {
        try {
            System.out.print(m);
            return Long.parseLong(scanner.nextLine());
        } catch (Exception e) { return -1; }
    }

    private static boolean lerBoolean(String m) {
        String resp = lerTexto(m).toLowerCase();
        return resp.startsWith("s");
    }

    private static Sexo lerSexo() {
        int op = lerInt("Sexo (1-Macho, 2-Femea): ");
        return op == 1 ? Sexo.MACHO : Sexo.FEMEA;
    }

    private static Porte lerPorte() {
        int op = lerInt("Porte (1-Pequeno, 2-Medio, 3-Grande): ");
        return op == 1 ? Porte.PEQUENO : (op == 2 ? Porte.MEDIO : Porte.GRANDE);
    }

    private static Integer tentarConverterParaInt(String t) {
        try { return Integer.parseInt(t); } catch (Exception e) { return null; }
    }
}