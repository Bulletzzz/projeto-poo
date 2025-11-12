package servico;

import fila.FilaPedidos;
import model.*;
import repositorio.Repositorio;

import java.util.ArrayList;
import java.util.List;

public class ServicoPedido {
    private final Repositorio<Pedido> repositorio;
    private final FilaPedidos fila;

    public ServicoPedido(Repositorio<Pedido> repositorio, FilaPedidos fila) {
        this.repositorio = repositorio;
        this.fila = fila;
    }

    public Pedido criar(Cliente cliente, List<ItemPedido> itens) {
        long id = repositorio.proximoId();
        Pedido pedido = Pedido.criar(id, cliente, itens);
        repositorio.adicionar(pedido);
        pedido.paraFila();
        fila.enqueue(pedido);
        return pedido;
    }

    public Pedido buscar(long id) {
        return repositorio.buscarPorId(id);
    }

    public List<Pedido> listar() {
        return repositorio.listarTodos();
    }
}