package laumiau;

import jakarta.persistence.EntityManager;
import laumiau.infra.JPAUtil;
import laumiau.model.*;
import laumiau.service.AdocoesService;
import laumiau.service.AnimalService;
import laumiau.service.ClienteService;
import laumiau.service.RelatorioService;
import org.flywaydb.core.Flyway;
import laumiau.repository.AnimalRepository;
import laumiau.repository.ClienteRepository;
import laumiau.repository.AdocoesRepository;
import laumiau.repository.UsuarioRepository;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static AnimalService animalService;
    private static ClienteService clienteService;
    private static AdocoesService adocoesService;
    private static RelatorioService relatorioService;

    public static void main(String[] args) {

        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:5432/postgres", "postgres", "2811")
                .locations("classpath:db/migration")
                .baselineOnMigrate(true)
                .load();
        flyway.migrate();
        System.out.println("Banco de dados atualizado com sucesso!");
        // jpa
        EntityManager em = JPAUtil.getEntityManager();
        // repositorios
        AnimalRepository animalRepository = new AnimalRepository(em);
        ClienteRepository clienteRepository = new ClienteRepository(em);
        AdocoesRepository adocoesRepository = new AdocoesRepository(em);
        UsuarioRepository usuarioRepository = new UsuarioRepository(em);
        // services
        animalService = new AnimalService(animalRepository);
        clienteService = new ClienteService(clienteRepository, usuarioRepository);
        adocoesService = new AdocoesService(adocoesRepository, animalRepository, clienteRepository);
        relatorioService = new RelatorioService(em);

        // inicia menu
        boolean executando = true;
        while (executando) {
            exibirMenuPrincipal();
            int opcao = lerInt("Escolha uma opção: ");

            switch (opcao) {
                case 1: cadastrarAnimal(); break;
                case 2: listarAnimais(); break;
                case 3: atualizarAnimal(); break;
                case 4: removerAnimal(); break;
                case 5: buscarAnimais(); break;
                case 6: registrarAdocao(); break;
                case 7: listarAdocoes(); break;
                case 8: cadastrarCliente(); break;
                case 9: relatorioService.gerarRelatorio(); break;
                case 0:
                    executando = false;
                    em.close();
                    JPAUtil.fechar();
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
        System.out.println("8. Cadastrar cliente");
        System.out.println("9. Relatório de negócio");
        System.out.println("0. Sair");
        System.out.println("==============================================");
    }

    private static void cadastrarAnimal() {
        System.out.println("\n--- Cadastro de Animal ---");
        try {
            String nome = lerTexto("Nome: ");
            String especie = lerTexto("Espécie: ");
            String raca = lerTexto("Raça: ");
            int idade = lerInt("Idade em meses: ");
            Sexo sexo = lerSexo();
            boolean vacinado = lerBoolean("Vacinado? (s/n): ");
            Porte porte = lerPorte();

            Animal animal = new Animal(nome, especie, raca, idade, sexo, vacinado, porte);
            animalService.cadastrar(animal);
            System.out.println("Animal cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void listarAnimais() {
        System.out.println("\n--- Lista de Animais ---");
        try {
            List<Animal> animais = animalService.listarTodos();
            if (animais.isEmpty()) {
                System.out.println("Nenhum animal cadastrado.");
                return;
            }
            for (Animal animal : animais) System.out.println(animal);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void atualizarAnimal() {
        System.out.println("\n--- Atualizar Animal ---");
        try {
            Long id = lerLong("Informe o ID do animal: ");
            Animal animal = animalService.buscarPorId(id);
            System.out.println("Animal encontrado: " + animal.getNome());

            String novoNome = lerTextoOpcional("Novo nome (Enter para manter): ");
            if (!novoNome.isEmpty()) animal.setNome(novoNome);

            String novaEspecie = lerTextoOpcional("Nova espécie (Enter para manter): ");
            if (!novaEspecie.isEmpty()) animal.setEspecie(novaEspecie);

            String novaRaca = lerTextoOpcional("Nova raça (Enter para manter): ");
            if (!novaRaca.isEmpty()) animal.setRaca(novaRaca);

            String idadeTexto = lerTextoOpcional("Nova idade em meses (Enter para manter): ");
            if (!idadeTexto.isEmpty()) {
                try {
                    int novaIdade = Integer.parseInt(idadeTexto);
                    if (novaIdade >= 0) animal.setIdade(novaIdade);
                    else System.out.println("Idade inválida. Campo não atualizado.");
                } catch (NumberFormatException e) {
                    System.out.println("Idade inválida. Campo não atualizado.");
                }
            }

            if (!animal.isVacinado()) {
                boolean desejaVacinar = lerBoolean("Deseja marcar como vacinado? (s/n): ");
                if (desejaVacinar) animal.vacinar();
            }

            animalService.atualizar(animal);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void removerAnimal() {
        System.out.println("\n--- Remover Animal ---");
        try {
            Long id = lerLong("Informe o ID do animal: ");
            Animal animal = animalService.buscarPorId(id);
            boolean confirmar = lerBoolean("Confirma remoção de '" + animal.getNome() + "'? (s/n): ");
            if (confirmar) {
                animalService.remover(id);
            } else {
                System.out.println("Remoção cancelada.");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void buscarAnimais() {
        System.out.println("\n--- Busca de Animais ---");
        try {
            List<Animal> animais = animalService.listarTodos();
            if (animais.isEmpty()) {
                System.out.println("Nenhum animal cadastrado.");
                return;
            }

            System.out.println("1. Buscar por nome");
            System.out.println("2. Buscar por espécie");
            System.out.println("3. Buscar por porte");
            System.out.println("4. Buscar por disponibilidade");
            int opcao = lerInt("Escolha o filtro: ");

            List<Animal> resultados = new java.util.ArrayList<>();

            switch (opcao) {
                case 1:
                    String nome = lerTexto("Digite parte do nome: ").toLowerCase(Locale.ROOT);
                    for (Animal a : animais)
                        if (a.getNome().toLowerCase(Locale.ROOT).contains(nome)) resultados.add(a);
                    break;
                case 2:
                    String especie = lerTexto("Digite a espécie: ").toLowerCase(Locale.ROOT);
                    for (Animal a : animais)
                        if (a.getEspecie().toLowerCase(Locale.ROOT).contains(especie)) resultados.add(a);
                    break;
                case 3:
                    Porte porte = lerPorte();
                    for (Animal a : animais)
                        if (a.getPorte() == porte) resultados.add(a);
                    break;
                case 4:
                    boolean somenteDisponiveis = lerBoolean("Mostrar apenas disponíveis? (s/n): ");
                    for (Animal a : animais) {
                        if (somenteDisponiveis && !a.isAdotado()) resultados.add(a);
                        else if (!somenteDisponiveis && a.isAdotado()) resultados.add(a);
                    }
                    break;
                default:
                    System.out.println("Filtro inválido.");
                    return;
            }

            if (resultados.isEmpty()) System.out.println("Nenhum animal encontrado.");
            else for (Animal a : resultados) System.out.println(a);

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void registrarAdocao() {
        System.out.println("\n--- Registrar Adoção ---");
        try {
            // mostra animais disponíveis
            List<Animal> disponiveis = animalService.listarTodos()
                    .stream()
                    .filter(a -> !a.isAdotado())
                    .toList();

            if (disponiveis.isEmpty()) {
                System.out.println("Não há animais disponíveis para adoção.");
                return;
            }

            System.out.println("Animais disponíveis:");
            for (Animal a : disponiveis) System.out.println(a);

            Long animalId = lerLong("ID do animal: ");
            Long clienteId = lerLong("ID do cliente: ");
            boolean termo = lerBoolean("Termo assinado? (s/n): ");

            adocoesService.registrarAdocao(animalId, clienteId, termo);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void listarAdocoes() {
        System.out.println("\n--- Lista de Adoções ---");
        try {
            List<Adocoes> adocoes = adocoesService.listarTodos();
            if (adocoes.isEmpty()) {
                System.out.println("Nenhuma adoção registrada.");
                return;
            }
            for (Adocoes a : adocoes) {
                System.out.println(a.gerarResumo());
                System.out.println("---------------------------------");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void cadastrarCliente() {
        System.out.println("\n--- Cadastro de Cliente ---");
        try {
            String nome = lerTexto("Nome: ");
            String email = lerTexto("Email: ");
            String senha = lerTexto("Senha: ");
            Cliente cliente = new Cliente(null, nome, email, senha);
            clienteService.cadastrar(cliente);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    // métodos auxiliares
    private static String lerTexto(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String valor = scanner.nextLine().trim();
            if (!valor.isEmpty()) return valor;
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
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite um número inteiro.");
            }
        }
    }

    private static Long lerLong(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite um número inteiro.");
            }
        }
    }

    private static boolean lerBoolean(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim().toLowerCase(Locale.ROOT);
            if (entrada.equals("s") || entrada.equals("sim")) return true;
            if (entrada.equals("n") || entrada.equals("nao") || entrada.equals("não")) return false;
            System.out.println("Entrada inválida. Digite 's' ou 'n'.");
        }
    }

    private static Sexo lerSexo() {
        while (true) {
            System.out.println("Sexo: 1-MACHO | 2-FEMEA");
            int opcao = lerInt("Escolha: ");
            if (opcao == 1) return Sexo.MACHO;
            if (opcao == 2) return Sexo.FEMEA;
            System.out.println("Opção inválida.");
        }
    }

    private static Porte lerPorte() {
        while (true) {
            System.out.println("Porte: 1-PEQUENO | 2-MEDIO | 3-GRANDE");
            int opcao = lerInt("Escolha: ");
            if (opcao == 1) return Porte.PEQUENO;
            if (opcao == 2) return Porte.MEDIO;
            if (opcao == 3) return Porte.GRANDE;
            System.out.println("Opção inválida.");
        }
    }
}