package model;

import enums.StatusPedido;
import exceptions.ValidacaoException;
import java.util.ArrayList;
import java.util.List;

public class Pedido extends Entidade {
    private final Cliente cliente;
    private final List<ItemPedido> itens = new ArrayList<>();
    private StatusPedido status = StatusPedido.ABERTO;

    private Pedido(int id, Cliente cliente) {
        super(id);
        this.cliente = cliente;
    }

    public static Pedido criar(int id, Cliente cliente) throws ValidacaoException {
        if (cliente == null) {
            throw new ValidacaoException("Cliente é obrigatório.");
        }
        return new Pedido(id, cliente);
    }

    public void adicionarItem(ItemPedido item) {
        itens.add(item);
    }

    public void validarItens() throws ValidacaoException {
        if (itens.isEmpty()) {
            throw new ValidacaoException("Pedido deve conter pelo menos um item.");
        }
    }

    public double calcularValorTotal() {
        double total = 0;
        for (ItemPedido item : itens) {
            total += item.calcularValor();
        }
        return total;
    }

    public synchronized void atualizarStatus(StatusPedido novoStatus) {
        this.status = novoStatus;
    }

    public synchronized StatusPedido obterStatus() {
        return status;
    }

    public Cliente obterCliente() {
        return cliente;
    }

    public List<ItemPedido> obterItens() {
        return new ArrayList<>(itens);
    }

    public void exibirInformacoes() {
        System.out.println("ID: " + id + ", Cliente: " + cliente.obterNome() + ", Status: " + status);
        System.out.println("Itens:");
        for (ItemPedido item : itens) {
            item.exibirInformacoes();
        }
        System.out.println("Total: " + calcularValorTotal());
    }

    @Override
    public String toCSV() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(",").append(cliente.obterId()).append(",").append(status.name());
        for (ItemPedido item : itens) {
            sb.append(",").append(item.toCSV());
        }
        return sb.toString();
    }

    @Override
    public void fromCSV(String linha) {
        
    }
}