package laumiau;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import laumiau.model.*;
import org.flywaydb.core.Flyway;


public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Flyway flyway = Flyway.configure()
                .dataSource(
                        "jdbc:postgresql://localhost:5432/postgres",
                        "postgres",
                        "2811"
                )
                .locations("classpath:db/migration")
                .baselineOnMigrate(true)
                .load();

        flyway.migrate();
        System.out.println("Banco de dados atualizado com sucesso!");

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
        boolean vacinado = lerBoolean("Vacinado? (s/n): ");
        Porte porte = lerPorte();

        Animal animal = new Animal(nome, especie, raca, idade, sexo, vacinado, porte);
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
        if (!novoNome.isEmpty()) {
            animal.setNome(novoNome);
        }

        String novaEspecie = lerTextoOpcional("Nova espécie (Enter para manter): ");
        if (!novaEspecie.isEmpty()) {
            animal.setEspecie(novaEspecie);
        }

        String novaRaca = lerTextoOpcional("Nova raça (Enter para manter): ");
        if (!novaRaca.isEmpty()) {
            animal.setRaca(novaRaca);
        }

        String idadeTexto = lerTextoOpcional("Nova idade (Enter para manter): ");
        if (!idadeTexto.isEmpty()) {
            Integer novaIdade = tentarConverterParaInt(idadeTexto);
            if (novaIdade == null || novaIdade < 0) {
                System.out.println("Idade inválida. Campo não atualizado.");
            } else {
                animal.setIdade(novaIdade);
            }
        }

        if (!animal.isVacinado()) {
            boolean desejaVacinar = lerBoolean("Deseja marcar como vacinado? (s/n): ");
            if (desejaVacinar) {
                animal.vacinar();
            }
        }

        System.out.println("Animal atualizado com sucesso.");
    }

    private static void removerAnimal(List<Animal> animais) {
        System.out.println("\n--- Remover Animal ---");
        if (animais.isEmpty()) {
            System.out.println("Não há animais para remover.");
            return;
        }

        long id = lerLong("Informe o ID do animal: ");
        Animal animal = buscarAnimalPorId(animais, id);

        if (animal == null) {
            System.out.println("Animal não encontrado.");
            return;
        }

        boolean confirmar = lerBoolean("Confirma remoção de '" + animal.getNome() + "'? (s/n): ");
        if (confirmar) {
            animais.remove(animal);
            System.out.println("Animal removido com sucesso.");
        } else {
            System.out.println("Remoção cancelada.");
        }
    }

    private static void buscarAnimais(List<Animal> animais) {
        System.out.println("\n--- Busca de Animais ---");
        if (animais.isEmpty()) {
            System.out.println("Nenhum animal cadastrado.");
            return;
        }

        System.out.println("1. Buscar por nome");
        System.out.println("2. Buscar por espécie");
        System.out.println("3. Buscar por porte");
        System.out.println("4. Buscar por disponibilidade");

        int opcao = lerInt("Escolha o filtro: ");

        List<Animal> resultados = new ArrayList<>();

        switch (opcao) {
            case 1:
                String nome = lerTexto("Digite parte do nome: ").toLowerCase(Locale.ROOT);
                for (Animal animal : animais) {
                    if (animal.getNome().toLowerCase(Locale.ROOT).contains(nome)) {
                        resultados.add(animal);
                    }
                }
                break;
            case 2:
                String especie = lerTexto("Digite a espécie: ").toLowerCase(Locale.ROOT);
                for (Animal animal : animais) {
                    if (animal.getEspecie().toLowerCase(Locale.ROOT).contains(especie)) {
                        resultados.add(animal);
                    }
                }
                break;
            case 3:
                Porte porte = lerPorte();
                for (Animal animal : animais) {
                    if (animal.getPorte() == porte) {
                        resultados.add(animal);
                    }
                }
                break;
            case 4:
                boolean somenteDisponiveis = lerBoolean("Mostrar apenas disponíveis para adoção? (s/n): ");
                for (Animal animal : animais) {
                    if (somenteDisponiveis && !animal.isAdotado()) {
                        resultados.add(animal);
                    } else if (!somenteDisponiveis && animal.isAdotado()) {
                        resultados.add(animal);
                    }
                }
                break;
            default:
                System.out.println("Filtro inválido.");
                return;
        }

        if (resultados.isEmpty()) {
            System.out.println("Nenhum animal encontrado para esse filtro.");
            return;
        }

        System.out.println("\nResultado da busca:");
        for (Animal animal : resultados) {
            System.out.println(animal);
        }
    }

    private static long registrarAdocao(List<Animal> animais, List<Adocoes> adocoes, long proximoIdAdocao) {
        System.out.println("\n--- Registrar Adoção ---");
        if (animais.isEmpty()) {
            System.out.println("Não há animais cadastrados para adoção.");
            return proximoIdAdocao;
        }

        List<Animal> disponiveis = new ArrayList<>();
        for (Animal animal : animais) {
            if (!animal.isAdotado()) {
                disponiveis.add(animal);
            }
        }

        if (disponiveis.isEmpty()) {
            System.out.println("Não há animais disponíveis para adoção.");
            return proximoIdAdocao;
        }

        System.out.println("Animais disponíveis:");
        for (Animal animal : disponiveis) {
            System.out.println(animal);
        }

        long idAnimal = lerLong("Informe o ID do animal para adoção: ");
        Animal animalEscolhido = buscarAnimalPorId(disponiveis, idAnimal);
        if (animalEscolhido == null) {
            System.out.println("Animal inválido ou indisponível para adoção.");
            return proximoIdAdocao;
        }

        String nomeAdotante = lerTexto("Nome do adotante: ");
        String telefoneAdotante = lerTexto("Telefone do adotante: ");
        boolean termoAssinado = lerBoolean("Termo assinado? (s/n): ");

        Adocoes novaAdocao = new Adocoes(proximoIdAdocao, animalEscolhido, nomeAdotante, telefoneAdotante, termoAssinado);
        animalEscolhido.adotar();
        adocoes.add(novaAdocao);

        System.out.println("Adoção registrada com sucesso! ID da adoção: " + proximoIdAdocao);
        System.out.println(novaAdocao.gerarResumo());

        return proximoIdAdocao + 1;
    }

    private static void listarAdocoes(List<Adocoes> adocoes) {
        System.out.println("\n--- Lista de Adoções ---");
        if (adocoes.isEmpty()) {
            System.out.println("Nenhuma adoção registrada.");
            return;
        }

        for (Adocoes adocao : adocoes) {
            System.out.println(adocao.gerarResumo());
            System.out.println("---------------------------------");
        }
    }

    private static void gerarRelatorio(List<Animal> animais, List<Adocoes> adocoes) {
        Relatorio relatorio = new Relatorio(animais, adocoes);
        relatorio.imprimirResumo();
    }

    private static Animal buscarAnimalPorId(List<Animal> animais, long id) {
        for (Animal animal : animais) {
            if (animal.getId() == id) {
                return animal;
            }
        }
        return null;
    }

    private static String lerTexto(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String valor = scanner.nextLine().trim();
            if (!valor.isEmpty()) {
                return valor;
            }
            System.out.println("Entrada inválida. Tente novamente.");
        }
    }

    private static String lerTextoOpcional(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    private static int lerInt(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();
            Integer valor = tentarConverterParaInt(entrada);
            if (valor != null) {
                return valor;
            }
            System.out.println("Valor inválido. Digite um número inteiro.");
        }
    }

    private static long lerLong(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();
            try {
                return Long.parseLong(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite um número inteiro.");
            }
        }
    }

    private static Integer tentarConverterParaInt(String texto) {
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static boolean lerBoolean(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim().toLowerCase(Locale.ROOT);
            if (entrada.equals("s") || entrada.equals("sim")) {
                return true;
            }
            if (entrada.equals("n") || entrada.equals("nao") || entrada.equals("não")) {
                return false;
            }
            System.out.println("Entrada inválida. Digite 's' ou 'n'.");
        }
    }

    private static Sexo lerSexo() {
        while (true) {
            System.out.println("Sexo: 1-MACHO | 2-FEMEA");
            int opcao = lerInt("Escolha: ");
            if (opcao == 1) {
                return Sexo.MACHO;
            }
            if (opcao == 2) {
                return Sexo.FEMEA;
            }
            System.out.println("Opção inválida.");
        }
    }

    private static Porte lerPorte() {
        while (true) {
            System.out.println("Porte: 1-PEQUENO | 2-MEDIO | 3-GRANDE");
            int opcao = lerInt("Escolha: ");
            if (opcao == 1) {
                return Porte.PEQUENO;
            }
            if (opcao == 2) {
                return Porte.MEDIO;
            }
            if (opcao == 3) {
                return Porte.GRANDE;
            }
            System.out.println("Opção inválida.");
        }
    }
}
