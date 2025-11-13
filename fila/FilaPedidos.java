package fila;

import java.util.LinkedList;
import java.util.Queue;
import model.Pedido;

public class FilaPedidos {
    private final Queue<Pedido> fila = new LinkedList<>();

    public synchronized void adicionar(Pedido pedido) {
        fila.add(pedido);
        notifyAll();
    }

    public synchronized Pedido remover() throws InterruptedException {
        while (fila.isEmpty()) {
            wait();
        }
        return fila.poll();
    }
}