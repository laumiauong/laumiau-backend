package laumiau.service;

import laumiau.model.Cliente;
import laumiau.repository.ClienteRepository;
import laumiau.repository.UsuarioRepository;
import java.util.List;

public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;

    // recebe os repositórios prontos em vez do EntityManager
    public ClienteService(ClienteRepository clienteRepository, UsuarioRepository usuarioRepository) {
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public void cadastrar(Cliente cliente) {
        if (usuarioRepository.buscarPorEmail(cliente.getEmail()) != null) {
            throw new RuntimeException("Email já cadastrado!");
        }
        clienteRepository.salvar(cliente);
        System.out.println("Cliente cadastrado com sucesso!");
    }

    public Cliente buscarPorId(Long id) {
        Cliente cliente = clienteRepository.buscarPorId(id);
        if (cliente == null) {
            throw new RuntimeException("Cliente não encontrado!");
        }
        return cliente;
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.listarTodos();
    }

    public void atualizar(Cliente cliente) {
        if (clienteRepository.buscarPorId(cliente.getId()) == null) {
            throw new RuntimeException("Cliente não encontrado!");
        }
        clienteRepository.atualizar(cliente);
        System.out.println("Cliente atualizado com sucesso!");
    }

    public void remover(Long id) {
        if (clienteRepository.buscarPorId(id) == null) {
            throw new RuntimeException("Cliente não encontrado!");
        }
        clienteRepository.remover(id);
        System.out.println("Cliente removido com sucesso!");
    }
}