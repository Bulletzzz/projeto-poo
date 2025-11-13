package processador;

import enums.StatusPedido;
import fila.FilaPedidos;
import model.Pedido;

public class ProcessadorPedidos extends Thread {
    private final FilaPedidos fila;
    private volatile boolean rodando = true;

    public ProcessadorPedidos(FilaPedidos fila) {
        this.fila = fila;
        setDaemon(true);
    }

    @Override
    public void run() {
        while (rodando) {
            try {
                Pedido pedido = fila.dequeue();
                synchronized (pedido) {
                    pedido.setStatus(StatusPedido.PROCESSANDO);
                }
                Thread.sleep(3000 + (long)(Math.random() * 2000));
                synchronized (pedido) {
                    pedido.setStatus(StatusPedido.FINALIZADO);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void parar() {
        rodando = false;
        interrupt();
    }

}
