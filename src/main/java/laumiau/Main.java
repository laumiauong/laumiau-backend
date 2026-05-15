package laumiau;

import jakarta.persistence.EntityManager;
import laumiau.infra.JPAUtil;
import laumiau.model.*;
import laumiau.service.*;
import laumiau.repository.*;
import org.flywaydb.core.Flyway;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static EntityManager em;
    private static AnimalService animalService;
    private static ClienteService clienteService;
    private static AdminService adminService;
    private static AdocoesService adocoesService;
    private static RelatorioService relatorioService;
    private static VacinaService vacinaService;
    private static UsuarioRepository usuarioRepository;

    private static Usuario usuarioLogado = null;

    public static void main(String[] args) {
        configurarBanco();
        inicializarServicos();

        boolean executando = true;
        while (executando) {
            if (usuarioLogado == null) {
                executando = exibirMenuLogin();
            } else {
                exibirMenuPrincipal();
            }
        }
    }

    private static void configurarBanco() {
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:5432/postgres", "postgres", "35784636")
                .locations("classpath:db/migration")
                .baselineOnMigrate(true)
                .load();
        flyway.repair();
        flyway.migrate(); // <- ADICIONAR ESTA LINHA
        System.out.println("Banco de dados atualizado com sucesso!");
        em = JPAUtil.getEntityManager();
    }

    private static void inicializarServicos() {
        AnimalRepository animalRepository = new AnimalRepository(em);
        ClienteRepository clienteRepository = new ClienteRepository(em);
        AdocoesRepository adocoesRepository = new AdocoesRepository(em);
        usuarioRepository = new UsuarioRepository(em);
        VacinaRepository vacinaRepository = new VacinaRepository(em);

        vacinaService = new VacinaService(vacinaRepository, animalRepository);
        animalService = new AnimalService(animalRepository);
        clienteService = new ClienteService(clienteRepository, usuarioRepository);
        adminService = new AdminService(usuarioRepository);
        adocoesService = new AdocoesService(adocoesRepository, animalRepository, clienteRepository);
        relatorioService = new RelatorioService(em);
    }

    // ==================== MENUS ====================

    private static boolean exibirMenuLogin() {
        System.out.println("\n========== LAUMIAU - ACESSO ==========");
        System.out.println("1. Login de Cliente");
        System.out.println("2. Cadastrar-se como Cliente");
        System.out.println("3. Login de Administrador");
        System.out.println("0. Sair");
        int opcao = lerInt("Escolha: ");

        switch (opcao) {
            case 1: realizarLoginCliente(); return true;
            case 2: cadastrarCliente(); return true;
            case 3: realizarLoginAdmin(); return true;
            case 0: return false;
            default: return true;
        }
    }

    private static void realizarLoginCliente() {
        System.out.println("\n--- LOGIN CLIENTE ---");
        String email = lerTexto("Email: ");
        String senha = lerTexto("Senha: ");

        Usuario usuario = usuarioRepository.buscarPorEmail(email);

        if (usuario != null && usuario.autenticar(senha) && usuario.getTipo() == TipoUsuario.cliente) {
            usuarioLogado = usuario;
            System.out.println("\nBem-vindo(a), " + usuario.getNome() + "!");
        } else {
            System.out.println("Erro: Email ou senha inválidos.");
        }
    }

    private static void realizarLoginAdmin() {
        System.out.println("\n--- LOGIN ADMINISTRADOR ---");
        String email = lerTexto("Email: ");
        String senha = lerTexto("Senha: ");

        Usuario usuario = usuarioRepository.buscarPorEmail(email);

        if (usuario != null && usuario.autenticar(senha) && usuario.getTipo() == TipoUsuario.admin) {
            usuarioLogado = usuario;
            System.out.println("\nBem-vindo(a), Administrador " + usuario.getNome() + "!");
        } else {
            System.out.println("Erro: Credenciais inválidas ou sem permissão de administrador.");
        }
    }

    private static void cadastrarAdmin() {
        System.out.println("\n--- Cadastro de Administrador ---");
        try {
            // Senha mestra para proteger o cadastro de admins
            String senhaMestra = lerTexto("Senha mestra: ");
            if (!senhaMestra.equals("laumiau@admin2025")) {
                System.out.println("Acesso negado: senha mestra incorreta.");
                return;
            }

            String nome = lerTexto("Nome: ");
            String email = lerTexto("Email: ");
            String senha = lerTexto("Senha: ");
            Admin admin = new Admin(null, nome, email, senha);
            adminService.cadastrar(admin);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n========== LAUMIAU - MENU PRINCIPAL ==========");
        System.out.println("Usuário: " + usuarioLogado.getNome() + " [" + usuarioLogado.getTipo() + "]");
        System.out.println("----------------------------------------------");

        System.out.println("1. Listar animais");
        System.out.println("2. Buscar animais (filtros)");
        System.out.println("3. Vacinas por animal");

        if (usuarioLogado.getTipo() == TipoUsuario.admin) {
            System.out.println("\n--- ÁREA ADMINISTRATIVA ---");
            System.out.println("4. Cadastrar animal");
            System.out.println("5. Atualizar animal");
            System.out.println("6. Remover animal");
            System.out.println("7. Registrar adoção");
            System.out.println("8. Listar adoções");
            System.out.println("9. Relatório de negócio");
            System.out.println("10. Consultas JOIN");
            System.out.println("11. Registrar vacina");
            System.out.println("12. Listar vacinas");
        }

        System.out.println("\n99. Logout");
        System.out.println("0. Sair do Sistema");

        int opcao = lerInt("\nEscolha uma opção: ");
        processarOpcao(opcao);
    }

    private static void processarOpcao(int opcao) {
        if (usuarioLogado.getTipo() != TipoUsuario.admin && opcao >= 4 && opcao <= 12) {
            System.out.println("Acesso Negado: Esta opção é exclusiva para administradores.");
            return;
        }

        switch (opcao) {
            case 1: listarAnimais(); break;
            case 2: buscarAnimais(); break;
            case 3: vacinasPorAnimal(); break;
            case 4: cadastrarAnimal(); break;
            case 5: atualizarAnimal(); break;
            case 6: removerAnimal(); break;
            case 7: registrarAdocao(); break;
            case 8: listarAdocoes(); break;
            case 9: relatorioService.gerarRelatorio(); break;
            case 10: exibirJoins(); break;
            case 11: registrarVacina(); break;
            case 12: listarVacinas(); break;
            case 99:
                usuarioLogado = null;
                System.out.println("Logout efetuado.");
                break;
            case 0:
                System.out.println("Encerrando... Até logo!");
                em.close();
                JPAUtil.fechar();
                System.exit(0);
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    // ==================== AUTH ====================

    private static void realizarLogin() {
        System.out.println("\n--- LOGIN ---");
        String email = lerTexto("Email: ");
        String senha = lerTexto("Senha: ");

        Usuario usuario = usuarioRepository.buscarPorEmail(email);

        if (usuario != null && usuario.autenticar(senha)) {
            usuarioLogado = usuario;
            System.out.println("\nBem-vindo(a), " + usuario.getNome() + "!");
            System.out.println("Perfil: " + usuario.getTipo().toString().toUpperCase());
        } else {
            System.out.println("Erro: Email ou senha inválidos.");
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

    // ==================== ANIMAIS ====================

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

    // ==================== ADOÇÕES ====================

    private static void registrarAdocao() {
        System.out.println("\n--- Registrar Adoção ---");
        try {
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

            System.out.println("\nClientes cadastrados:");
            List<Cliente> clientes = clienteService.listarTodos();
            if (clientes.isEmpty()) {
                System.out.println("Nenhum cliente cadastrado.");
                return;
            }
            for (Cliente c : clientes) System.out.println(c);

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

    // ==================== JOINS ====================

    private static void exibirJoins() {
        System.out.println("\n--- Consultas JOIN ---");
        System.out.println("1. INNER JOIN - Adoções com animal e cliente");
        System.out.println("2. LEFT JOIN  - Todos os animais com adoções");
        System.out.println("3. RIGHT JOIN - Todas as adoções com animais");
        System.out.println("4. FULL JOIN  - Todos os animais e adoções");

        int opcao = lerInt("Escolha: ");
        JoinRepository joinRepo = new JoinRepository(em);

        try {
            switch (opcao) {
                case 1:
                    System.out.println("\n--- INNER JOIN ---");
                    joinRepo.imprimirResultados(
                            joinRepo.innerJoinAdocoes(),
                            new String[]{"ID Adoção", "Animal", "Espécie", "Cliente", "Data", "Termo"}
                    );
                    break;
                case 2:
                    System.out.println("\n--- LEFT JOIN ---");
                    joinRepo.imprimirResultados(
                            joinRepo.leftJoinAnimais(),
                            new String[]{"ID Animal", "Nome", "Espécie", "Status", "ID Adoção", "Data"}
                    );
                    break;
                case 3:
                    System.out.println("\n--- RIGHT JOIN ---");
                    joinRepo.imprimirResultados(
                            joinRepo.rightJoinAdocoes(),
                            new String[]{"ID Animal", "Nome", "ID Adoção", "Data"}
                    );
                    break;
                case 4:
                    System.out.println("\n--- FULL JOIN ---");
                    joinRepo.imprimirResultados(
                            joinRepo.fullJoinAnimaisAdocoes(),
                            new String[]{"ID Animal", "Nome", "ID Adoção", "Data"}
                    );
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    // ==================== VACINAS ====================

    private static void registrarVacina() {
        System.out.println("\n--- Registrar Vacina ---");
        try {
            Long animalId = lerLong("ID do animal: ");
            String nome = lerTexto("Nome da vacina: ");
            LocalDate dataAplicacao = LocalDate.now();
            String proximaTexto = lerTextoOpcional("Próxima dose (dd-MM-yyyy, Enter para pular): ");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate proximaDose = proximaTexto.isEmpty() ? null : LocalDate.parse(proximaTexto, formatter);

            vacinaService.registrar(animalId, nome, dataAplicacao, proximaDose);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void listarVacinas() {
        System.out.println("\n--- Lista de Vacinas ---");
        try {
            var vacinas = vacinaService.listarTodos();
            if (vacinas.isEmpty()) System.out.println("Nenhuma vacina registrada.");
            else vacinas.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void vacinasPorAnimal() {
        System.out.println("\n--- Vacinas por Animal ---");
        try {
            Long animalId = lerLong("ID do animal: ");
            var vacinas = vacinaService.listarPorAnimal(animalId);
            if (vacinas.isEmpty()) System.out.println("Nenhuma vacina encontrada para este animal.");
            else vacinas.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    // ==================== UTILITÁRIOS ====================

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