package laumiau.service;

import jakarta.persistence.EntityManager;
import laumiau.model.Animal;
import laumiau.model.StatusAnimal;
import laumiau.repository.AnimalRepository;

import java.sql.SQLOutput;
import java.util.List;

public class AnimalService {

    private final AnimalRepository repository;

    public AnimalService(AnimalRepository repository) {
        this.repository = repository;
    }

    // animal so pode ser cadastrado se tiver nome e especie
    public void cadastrar(Animal animal) {
        if (animal.getNome() == null || animal.getNome().isBlank()) {
            throw new RuntimeException("Nome do animal é obrigatório!");
        }
        if (animal.getEspecie() == null || animal.getEspecie().isBlank()) {
            throw new RuntimeException("Nome da espécie é obrigatório!");
        }
        repository.salvar(animal);
        System.out.println("Animal cadastrado com sucesso!");
    }

    // só pode adotar animal disponivel
    public void adotar(Long id) {
        Animal animal = repository.buscarPorId(id);
        if (animal == null) {
            throw new RuntimeException("Animal não encontrado!");
        }
        if (animal.getStatus() == StatusAnimal.ADOTADO) {
            throw new RuntimeException("Esse animal já foi adotado!");
        }
        animal.adotar();
        repository.atualizar(animal);
        System.out.println("Adoção registrada com sucesso!");
    }
    public Animal buscarPorId(Long id){
        Animal animal = repository.buscarPorId(id);
        if (animal == null) {
            throw new RuntimeException("Animal não encontrado");
        }
        return animal;
    }
    public List<Animal> listarTodos(){
        return repository.listarTodos();
    }
    public void atualizar(Animal animal) {
        if (repository.buscarPorId(animal.getId()) == null) {
            throw new RuntimeException("Animal não encontrado!");
        }
        repository.atualizar(animal);
        System.out.println("Animal atualizado com sucesso!");
    }
    public void remover(Long id) {
        Animal animal = repository.buscarPorId(id);
        if  (animal == null) {
            throw new RuntimeException("Animal não encontrado!");
        }
        if  (animal.getStatus() == StatusAnimal.ADOTADO) {
            throw new RuntimeException("Não é possível remover um animal já adotado!");
        }
        repository.deletar(id);
        System.out.println("Animal removido com sucesso!");
    }
}

