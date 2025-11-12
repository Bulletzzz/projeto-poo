package model;

import enums.StatusPedido;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Pedido extends Entidade {
    private final Cliente cliente;
    private final List<ItemPedido> itens;
    private StatusPedido status;

    private Pedido(long id, Cliente cliente, List<ItemPedido> itens) {
        super(id);
        this.cliente = cliente;
        this.itens = new ArrayList<>(itens);
        this.status = StatusPedido.ABERTO;
    }

    public static Pedido criar(long id, Cliente cliente, List<ItemPedido> itens) {
        if (itens.isEmpty()) throw new IllegalArgumentException("Pedido deve ter pelo menos um item.");
        return new Pedido(id, cliente, itens);
    }

    public Cliente getCliente() { return cliente; }
    public List<ItemPedido> getItens() { return Collections.unmodifiableList(itens); }
    public StatusPedido getStatus() { return status; }
    public double getValorTotal() {
        return itens.stream().mapToDouble(ItemPedido::getSubtotal).sum();
    }

    public synchronized void setStatus(StatusPedido status) {
        this.status = status;
    }

    public synchronized void paraFila() {
        if (status != StatusPedido.ABERTO) throw new IllegalStateException("Só pode ir para fila se estiver ABERTO");
        this.status = StatusPedido.FILA;
    }
}