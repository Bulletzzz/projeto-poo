package servico;

import enums.CategoriaProduto;
import exceptions.ValidacaoException;
import model.Produto;
import repositorio.Repositorio;

import java.util.List;

public class ServicoProduto {
    private final Repositorio<Produto> repositorio;

    public ServicoProduto(Repositorio<Produto> repositorio) {
        this.repositorio = repositorio;
    }

    public Produto cadastrar(String nome, double preco, CategoriaProduto categoria) throws ValidacaoException {
        long id = repositorio.proximoId();
        Produto produto = Produto.criar(id, nome, preco, categoria);
        repositorio.adicionar(produto);
        return produto;
    }

    public Produto buscar(long id) {
        return repositorio.buscarPorId(id);
    }

    public List<Produto> listar() {
        return repositorio.listarTodos();
    }
}