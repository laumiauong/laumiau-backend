package laumiau.service;

import laumiau.model.Animal;
import laumiau.model.Vacina;
import laumiau.repository.AnimalRepository;
import laumiau.repository.VacinaRepository;

import java.time.LocalDate;
import java.util.List;

public class VacinaService {

    private final VacinaRepository vacinaRepository;
    private final AnimalRepository animalRepository;

    public VacinaService(VacinaRepository vacinaRepository, AnimalRepository animalRepository) {
        this.vacinaRepository = vacinaRepository;
        this.animalRepository = animalRepository;
    }

    public void registrar(Long animalId, String nome, LocalDate dataAplicacao, LocalDate proximaDose) {
        Animal animal = animalRepository.buscarPorId(animalId);
        if (animal == null) throw new RuntimeException("Animal não encontrado!");

        Vacina vacina = new Vacina(animal, nome, dataAplicacao, proximaDose);
        vacinaRepository.salvar(vacina);

        // marca animal como vacinado automaticamente
        if (!animal.isVacinado()) {
            animal.vacinar();
            animalRepository.atualizar(animal);
        }

        System.out.println("Vacina registrada com sucesso!");
    }

    public List<Vacina> listarTodos() {
        return vacinaRepository.listarTodos();
    }

    public List<Vacina> listarPorAnimal(Long animalId) {
        return vacinaRepository.listarPorAnimal(animalId);
    }

    public void remover(Long id) {
        Vacina vacina = vacinaRepository.buscarPorId(id);
        if (vacina == null) throw new RuntimeException("Vacina não encontrada!");
        vacinaRepository.deletar(id);
        System.out.println("Vacina removida com sucesso!");
    }
}