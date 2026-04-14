package laumiau.service;

import laumiau.model.Adocoes;
import laumiau.model.Animal;
import laumiau.model.Cliente;
import laumiau.model.StatusAnimal;
import laumiau.repository.AdocoesRepository;
import laumiau.repository.AnimalRepository;
import laumiau.repository.ClienteRepository;
import java.util.List;

public class AdocoesService {

    private final AdocoesRepository adocoesRepository;
    private final AnimalRepository animalRepository;
    private final ClienteRepository clienteRepository;

    public AdocoesService(AdocoesRepository adocoesRepository, AnimalRepository animalRepository, ClienteRepository clienteRepository) {
        this.adocoesRepository = adocoesRepository;
        this.animalRepository = animalRepository;
        this.clienteRepository = clienteRepository;
    }

    public void registrarAdocao(Long animalId, Long clienteId, boolean termoAssinado) {
        Animal animal = animalRepository.buscarPorId(animalId);
        if (animal == null) {
            throw new RuntimeException("Animal não encontrado!");
        }
        if (animal.getStatus() == StatusAnimal.ADOTADO) {
            throw new RuntimeException("Esse animal já foi adotado!");
        }

        Cliente cliente = clienteRepository.buscarPorId(clienteId);
        if (cliente == null) {
            throw new RuntimeException("Cliente não encontrado!");
        }

        // salva a adoção
        Adocoes adocao = new Adocoes(animal, cliente, termoAssinado);
        adocoesRepository.salvar(adocao);

        System.out.println("Adoção registrada com sucesso!");
    }

    public Adocoes buscarPorId(Long id) {
        Adocoes adocao = adocoesRepository.buscarPorId(id);
        if (adocao == null) {
            throw new RuntimeException("Adoção não encontrada!");
        }
        return adocao;
    }

    public List<Adocoes> listarTodos() {
        return adocoesRepository.listarTodos();
    }

    public void atualizar(Adocoes adocao) {
        if (adocoesRepository.buscarPorId(adocao.getIdAdocao()) == null) {
            throw new RuntimeException("Adoção não encontrada!");
        }
        adocoesRepository.atualizar(adocao);
        System.out.println("Adoção atualizada com sucesso!");
    }

    public void remover(Long id) {
        if (adocoesRepository.buscarPorId(id) == null) {
            throw new RuntimeException("Adoção não encontrada!");
        }
        adocoesRepository.deletar(id);
        System.out.println("Adoção removida com sucesso!");
    }
}