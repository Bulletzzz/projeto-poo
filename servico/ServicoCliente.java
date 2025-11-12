package servico;

import exceptions.ValidacaoException;
import model.Cliente;
import repositorio.Repositorio;

import java.util.List;

public class ServicoCliente {
    private final Repositorio<Cliente> repositorio;

    public ServicoCliente(Repositorio<Cliente> repositorio) {
        this.repositorio = repositorio;
    }

    public Cliente cadastrar(String nome, String email) throws ValidacaoException {
        long id = repositorio.proximoId();
        Cliente cliente = Cliente.criar(id, nome, email);
        repositorio.adicionar(cliente);
        return cliente;
    }

    public Cliente buscar(long id) {
        return repositorio.buscarPorId(id);
    }

    public List<Cliente> listar() {
        return repositorio.listarTodos();
    }
}