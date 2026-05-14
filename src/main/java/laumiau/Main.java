package laumiau;

import jakarta.persistence.EntityManager;

import javax.swing.SwingUtilities;

import laumiau.infra.JPAUtil;
import laumiau.repository.AdocoesRepository;
import laumiau.repository.AnimalRepository;
import laumiau.repository.ClienteRepository;
import laumiau.repository.UsuarioRepository;
import laumiau.repository.VacinaRepository;

import laumiau.service.AdocoesService;
import laumiau.service.AnimalService;
import laumiau.service.ClienteService;
import laumiau.service.RelatorioService;
import laumiau.service.VacinaService;

import laumiau.view.AnimalView;

import org.flywaydb.core.Flyway;

public class Main {

    private static EntityManager em;

    private static AnimalService animalService;
    private static ClienteService clienteService;
    private static AdocoesService adocoesService;
    private static RelatorioService relatorioService;
    private static VacinaService vacinaService;

    public static void main(String[] args) {

        Flyway flyway = Flyway.configure()
                .dataSource(
                        "jdbc:postgresql://localhost:5432/postgres",
                        "postgres",
                        "123"
                )
                .locations("classpath:db/migration")
                .baselineOnMigrate(true)
                .outOfOrder(true)
                .load();

        flyway.repair();
        flyway.migrate();

        System.out.println("Banco de dados atualizado com sucesso!");

        em = JPAUtil.getEntityManager();

        AnimalRepository animalRepository =
                new AnimalRepository(em);

        ClienteRepository clienteRepository =
                new ClienteRepository(em);

        AdocoesRepository adocoesRepository =
                new AdocoesRepository(em);

        UsuarioRepository usuarioRepository =
                new UsuarioRepository(em);

        VacinaRepository vacinaRepository =
                new VacinaRepository(em);

        animalService =
                new AnimalService(animalRepository);

        clienteService =
                new ClienteService(clienteRepository, usuarioRepository);

        adocoesService =
                new AdocoesService(
                        adocoesRepository,
                        animalRepository,
                        clienteRepository
                );

        relatorioService =
                new RelatorioService(em);

        vacinaService =
                new VacinaService(
                        vacinaRepository,
                        animalRepository
                );

        SwingUtilities.invokeLater(() -> {
            new AnimalView(animalService);
        });
    }
}