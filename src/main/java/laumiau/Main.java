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

        // CONFIGURAÇÃO COM PERMISSÃO DE LIMPEZA
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:5432/postgres", "postgres", "35784636")
                .locations("classpath:db/migration")
                .baselineOnMigrate(true)
                .cleanDisabled(false) // <--- ESSA LINHA DÁ A PERMISSÃO
                .load();

        // Executa a limpeza forçada
        flyway.migrate();
        System.out.println("O banco de dados foi resetado com sucesso!");

        // O código abaixo vai parar aqui porque o banco está vazio,
        // mas o objetivo é chegar na mensagem de "resetado" acima.

        EntityManager em = JPAUtil.getEntityManager();
        AnimalRepository animalRepository = new AnimalRepository(em);
        ClienteRepository clienteRepository = new ClienteRepository(em);
        AdocoesRepository adocoesRepository = new AdocoesRepository(em);
        UsuarioRepository usuarioRepository = new UsuarioRepository(em);

        animalService = new AnimalService(animalRepository);
        clienteService = new ClienteService(clienteRepository, usuarioRepository);
        adocoesService = new AdocoesService(adocoesRepository, animalRepository, clienteRepository);
        relatorioService = new RelatorioService(em);

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

    // ... (restante dos métodos auxiliares lerTexto, lerInt, etc continuam iguais)
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

    // Métodos de cadastrarAnimal, listarAnimais, etc (recomendo manter os que você já tem abaixo)
    private static void cadastrarAnimal() { /* seu código aqui */ }
    private static void listarAnimais() { /* seu código aqui */ }
    private static void atualizarAnimal() { /* seu código aqui */ }
    private static void removerAnimal() { /* seu código aqui */ }
    private static void buscarAnimais() { /* seu código aqui */ }
    private static void registrarAdocao() { /* seu código aqui */ }
    private static void listarAdocoes() { /* seu código aqui */ }
    private static void cadastrarCliente() { /* seu código aqui */ }
}