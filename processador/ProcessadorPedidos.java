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
                Pedido pedido = fila.remover();
                pedido.atualizarStatus(StatusPedido.PROCESSANDO);
                System.out.println("Processando pedido ID: " + pedido.obterId());
                Thread.sleep((int) (Math.random() * 2000 + 3000)); 
                pedido.atualizarStatus(StatusPedido.FINALIZADO);
                System.out.println("Pedido ID: " + pedido.obterId() + " finalizado.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void parar() {
        rodando = false;
        interrupt();
    }
