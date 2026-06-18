package laumiau.service;

import laumiau.model.*;
import laumiau.repository.*;

import java.util.List;

public class AdocoesService {

    private final AdocoesRepository            adocoesRepository;
    private final AnimalRepository             animalRepository;
    private final ClienteRepository            clienteRepository;
    private final SolicitacaoAdocaoRepository  solicitacaoRepository;

    public AdocoesService(AdocoesRepository adocoesRepository,
                          AnimalRepository animalRepository,
                          ClienteRepository clienteRepository,
                          SolicitacaoAdocaoRepository solicitacaoRepository) {
        this.adocoesRepository    = adocoesRepository;
        this.animalRepository     = animalRepository;
        this.clienteRepository    = clienteRepository;
        this.solicitacaoRepository = solicitacaoRepository;
    }

    public void registrarAdocao(Long animalId, Long clienteId,
                                boolean termoAssinado,
                                String telefone, String cpf,
                                String dataNascimento, String profissao,
                                String tipoMoradia, String possuiQuintal,
                                String tevePetsAntes, String outrosPets,
                                String motivoAdocao) {

        Animal animal = animalRepository.buscarPorId(animalId);
        if (animal == null)
            throw new RuntimeException("Animal não encontrado!");
        if (animal.getStatus() == StatusAnimal.ADOTADO)
            throw new RuntimeException("Esse animal já foi adotado!");

        Cliente cliente = clienteRepository.buscarPorId(clienteId);
        if (cliente == null)
            throw new RuntimeException("Cliente não encontrado!");

        if (!termoAssinado)
            throw new RuntimeException("É necessário aceitar os termos de adoção.");

        Adocoes adocao = new Adocoes(animal, cliente, true);
        adocoesRepository.salvar(adocao);

        SolicitacaoAdocao solicitacao = new SolicitacaoAdocao(
                adocao, telefone, cpf, dataNascimento, profissao,
                tipoMoradia, possuiQuintal, tevePetsAntes, outrosPets, motivoAdocao
        );
        solicitacaoRepository.salvar(solicitacao);
    }

    public void aprovarAdocao(Long idAdocao) {
        Adocoes adocao = adocoesRepository.buscarPorId(idAdocao);
        if (adocao == null)
            throw new RuntimeException("Adoção não encontrada!");

        adocao.aprovar();
        Animal animal = adocao.getAnimal();
        animal.setStatus(StatusAnimal.ADOTADO);

        adocoesRepository.atualizar(adocao);
        animalRepository.atualizar(animal);
    }

    public void recusarAdocao(Long idAdocao) {
        Adocoes adocao = adocoesRepository.buscarPorId(idAdocao);
        if (adocao == null)
            throw new RuntimeException("Adoção não encontrada!");

        adocao.recusar();
        adocoesRepository.atualizar(adocao);
        animalRepository.atualizar(adocao.getAnimal());
    }

    public Adocoes buscarPorId(Long id) {
        Adocoes adocao = adocoesRepository.buscarPorId(id);
        if (adocao == null)
            throw new RuntimeException("Adoção não encontrada!");
        return adocao;
    }

    public List<Adocoes> listarTodos() {
        return adocoesRepository.listarTodos();
    }

    public List<SolicitacaoAdocao> listarSolicitacoes() {
        return solicitacaoRepository.listarTodos();
    }

    public SolicitacaoAdocao buscarSolicitacaoPorAdocao(Long adocaoId) {
        return solicitacaoRepository.buscarPorAdocaoId(adocaoId);
    }

    public void remover(Long id) {
        if (adocoesRepository.buscarPorId(id) == null)
            throw new RuntimeException("Adoção não encontrada!");
        adocoesRepository.deletar(id);
    }
}