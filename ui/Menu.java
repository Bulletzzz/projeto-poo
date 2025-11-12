package ui;

import enums.CategoriaProduto;
import enums.StatusPedido;
import exceptions.ValidacaoException;
import fila.FilaPedidos;
import model.*;
import processador.ProcessadorPedidos;
import repositorio.Repositorio;
import repositorio.impl.RepositorioEmMemoria;
import servico.ServicoCliente;
import servico.ServicoPedido;
import servico.ServicoProduto;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private final Scanner scanner = new Scanner(System.in);
    private final Repositorio<Cliente> repoCliente = new RepositorioEmMemoria<>();
    private final Repositorio<Produto> repoProduto = new RepositorioEmMemoria<>();
    private final Repositorio<Pedido> repoPedido = new RepositorioEmMemoria<>();
    private final FilaPedidos fila = new FilaPedidos();
    private final ProcessadorPedidos processador;
    private final ServicoCliente servicoCliente;
    private final ServicoProduto servicoProduto;
    private final ServicoPedido servicoPedido;

    public Menu() {
        this.servicoCliente = new ServicoCliente(repoCliente);
        this.servicoProduto = new ServicoProduto(repoProduto);
        this.servicoPedido = new ServicoPedido(repoPedido, fila);
        this.processador = new ProcessadorPedidos(fila);
        this.processador.start();
    }

    public void executar() {
        while (true) {
            exibirMenu();
            int opcao = lerInteiro("Opção");
            switch (opcao) {
                case 1 -> cadastrarCliente();
                case 2 -> cadastrarProduto();
                case 3 -> criarPedido();
                case 4 -> listarClientes();
                case 5 -> listarProdutos();
                case 6 -> listarPedidos();
                case 0 -> {
                    processador.encerrar();
                    System.out.println("Saindo...");
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void exibirMenu() {
        System.out.println("\n=== Sistema de Gestão de Pedidos ===");
        System.out.println("1. Cadastrar Cliente");
        System.out.println("2. Cadastrar Produto");
        System.out.println("3. Criar Pedido");
        System.out.println("4. Listar Clientes");
        System.out.println("5. Listar Produtos");
        System.out.println("6. Listar Pedidos");
        System.out.println("0. Sair");
        System.out.print("-> ");
    }

    private void cadastrarCliente() {
        System.out.println("\n--- Cadastro de Cliente ---");
        String nome = lerString("Nome");
        String email = lerString("E-mail");
        try {
            Cliente c = servicoCliente.cadastrar(nome, email);
            System.out.println("Cliente cadastrado: ID " + c.getId());
        } catch (ValidacaoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void cadastrarProduto() {
        System.out.println("\n--- Cadastro de Produto ---");
        String nome = lerString("Nome");
        double preco = lerDouble("Preço");
        System.out.println("Categorias: ALIMENTOS, ELETRONICOS, LIVROS");
        String catStr = lerString("Categoria").toUpperCase();
        try {
            CategoriaProduto categoria = CategoriaProduto.valueOf(catStr);
            Produto p = servicoProduto.cadastrar(nome, preco, categoria);
            System.out.println("Produto cadastrado: ID " + p.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("Categoria inválida.");
        } catch (ValidacaoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void criarPedido() {
        System.out.println("\n--- Criar Pedido ---");
        long clienteId = lerLong("ID do Cliente");
        Cliente cliente = servicoCliente.buscar(clienteId);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        List<ItemPedido> itens = new ArrayList<>();
        while (true) {
            long prodId = lerLong("ID do Produto (0 para finalizar)");
            if (prodId == 0) break;
            Produto p = servicoProduto.buscar(prodId);
            if (p == null) {
                System.out.println("Produto não encontrado.");
                continue;
            }
            int qtd = lerInteiro("Quantidade");
            itens.add(new ItemPedido(p, qtd));
        }

        if (itens.isEmpty()) {
            System.out.println("Pedido precisa de pelo menos um item.");
            return;
        }

        Pedido pedido = servicoPedido.criar(cliente, itens);
        System.out.println("Pedido criado: ID " + pedido.getId() + " | Total: R$" + String.format("%.2f", pedido.getValorTotal()));
        System.out.println("Status: " + pedido.getStatus() + " (em processamento em segundo plano)");
    }

    private void listarClientes() {
        System.out.println("\n--- Clientes ---");
        for (Cliente c : servicoCliente.listar()) {
            System.out.printf("ID: %d | Nome: %s | E-mail: %s%n", c.getId(), c.getNome(), c.getEmail());
        }
    }

    private void listarProdutos() {
        System.out.println("\n--- Produtos ---");
        for (Produto p : servicoProduto.listar()) {
            System.out.printf("ID: %d | Nome: %s | Preço: R$%.2f | Categoria: %s%n",
                    p.getId(), p.getNome(), p.getPreco(), p.getCategoria());
        }
    }

    private void listarPedidos() {
        System.out.println("\n--- Pedidos ---");
        for (Pedido p : servicoPedido.listar()) {
            System.out.printf("ID: %d | Cliente: %s | Status: %s | Total: R$%.2f%n",
                    p.getId(), p.getCliente().getNome(), p.getStatus(), p.getValorTotal());
            System.out.println("Itens:");
            for (ItemPedido item : p.getItens()) {
                System.out.printf("  - %s x%d = R$%.2f%n",
                        item.getProduto().getNome(), item.getQuantidade(), item.getSubtotal());
            }
        }
    }

    private String lerString(String campo) {
        System.out.print(campo + ": ");
        return scanner.nextLine().trim();
    }

    private int lerInteiro(String campo) {
        while (true) {
            try {
                return Integer.parseInt(lerString(campo));
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }
    }

    private long lerLong(String campo) {
        while (true) {
            try {
                return Long.parseLong(lerString(campo));
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }
    }

    private double lerDouble(String campo) {
        while (true) {
            try {
                return Double.parseDouble(lerString(campo));
            } catch (NumberFormatException e) {
                System.out.println("Digite um número decimal válido.");
            }
        }
    }
}