package fila;

import model.Pedido;

import java.util.LinkedList;
import java.util.Queue;

public class FilaPedidos {
    private final Queue<Pedido> fila = new LinkedList<>();

    public synchronized void enqueue(Pedido pedido) {
        fila.add(pedido);
        notifyAll();
    }

    public synchronized Pedido dequeue() throws InterruptedException {
        while (fila.isEmpty()) {
            wait();
        }
        return fila.poll();
    }

    public synchronized boolean isEmpty() {
        return fila.isEmpty();
    }
}