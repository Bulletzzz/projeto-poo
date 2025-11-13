package servico;

import model.Pedido;
import model.ItemPedido;
import model.Cliente;
import model.Produto;
import fila.FilaPedidos;
import repositorio.Repositorio;
import exceptions.ValidacaoException;

import java.util.Scanner;

public class ServicoPedido {
    private final Repositorio<Pedido> repoPedidos;
    private final Repositorio<Cliente> repoClientes;
    private final Repositorio<Produto> repoProdutos;
    private final FilaPedidos fila;

    public ServicoPedido(Repositorio<Pedido> repoPedidos, Repositorio<Cliente> repoClientes, Repositorio<Produto> repoProdutos, FilaPedidos fila) {
        this.repoPedidos = repoPedidos;
        this.repoClientes = repoClientes;
        this.repoProdutos = repoProdutos;
        this.fila = fila;
    }

    public void criar(Scanner scanner) {
        try {
            System.out.print("ID Cliente: ");
            int idCliente = scanner.nextInt();
            scanner.nextLine();
            Cliente cliente = repoClientes.encontrarPorId(idCliente);
            if (cliente == null) {
                throw new ValidacaoException("Cliente não encontrado.");
            }
            int idPedido = repoPedidos.obterProximoId();
            Pedido pedido = Pedido.criar(idPedido, cliente);
            boolean adicionando = true;
            while (adicionando) {
                System.out.print("ID Produto: ");
                int idProduto = scanner.nextInt();
                scanner.nextLine();
                Produto produto = repoProdutos.encontrarPorId(idProduto);
                if (produto == null) {
                    System.out.println("Produto não encontrado.");
                    continue;
                }
                System.out.print("Quantidade: ");
                int qtd = scanner.nextInt();
                scanner.nextLine();
                ItemPedido item = ItemPedido.criar(produto, qtd);
                pedido.adicionarItem(item);
                System.out.print("Adicionar mais? (s/n): ");
                adicionando = scanner.nextLine().equalsIgnoreCase("s");
            }
            pedido.validarItens();
            pedido.atualizarStatus(enums.StatusPedido.FILA);
            repoPedidos.adicionar(pedido);
            fila.adicionar(pedido);
            System.out.println("Pedido criado e adicionado à fila.");
        } catch (ValidacaoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void listar() {
        for (Pedido p : repoPedidos.listarTodos()) {
            p.exibirInformacoes();
        }
    }
}