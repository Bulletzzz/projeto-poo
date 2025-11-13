package repositorio.impl;

import model.Pedido;
import model.ItemPedido;
import model.Cliente;
import model.Produto;
import repositorio.Repositorio;
import enums.StatusPedido;
import exceptions.ValidacaoException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class RepositorioPedidos implements Repositorio<Pedido> {
    private final Map<Integer, Pedido> pedidos = new HashMap<>();
    private final AtomicInteger proximoId = new AtomicInteger(1);
    private final Repositorio<Cliente> repoClientes;
    private final Repositorio<Produto> repoProdutos;

    public RepositorioPedidos(Repositorio<Cliente> repoClientes, Repositorio<Produto> repoProdutos) {
        this.repoClientes = repoClientes;
        this.repoProdutos = repoProdutos;
    }

    @Override
    public void adicionar(Pedido pedido) {
        pedidos.put(pedido.obterId(), pedido);
    }

    @Override
    public Pedido encontrarPorId(int id) {
        return pedidos.get(id);
    }

    @Override
    public List<Pedido> listarTodos() {
        return new ArrayList<>(pedidos.values());
    }

    @Override
    public int obterProximoId() {
        return proximoId.getAndIncrement();
    }

    @Override
    public void salvarEmArquivo(String arquivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
            for (Pedido p : pedidos.values()) {
                writer.write(p.toCSV());
                writer.newLine();
            }
        }
    }

    @Override
    public void carregarDeArquivo(String arquivo) throws IOException, ValidacaoException {
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                int id = Integer.parseInt(partes[0]);
                int idCliente = Integer.parseInt(partes[1]);
                Cliente cliente = repoClientes.encontrarPorId(idCliente);
                if (cliente == null) {
                    throw new ValidacaoException("Cliente não encontrado para ID: " + idCliente);
                }
                Pedido pedido = Pedido.criar(id, cliente);
                pedido.atualizarStatus(StatusPedido.valueOf(partes[2]));
                for (int i = 3; i < partes.length; i++) {
                    String[] itemDados = partes[i].split(":");
                    int idProduto = Integer.parseInt(itemDados[0]);
                    int qtd = Integer.parseInt(itemDados[1]);
                    Produto produto = repoProdutos.encontrarPorId(idProduto);
                    if (produto == null) {
                        throw new ValidacaoException("Produto não encontrado para ID: " + idProduto);
                    }
                    ItemPedido item = ItemPedido.criar(produto, qtd);
                    pedido.adicionarItem(item);
                }
                adicionar(pedido);
                proximoId.set(Math.max(proximoId.get(), id + 1));
            }
        }
    }
}