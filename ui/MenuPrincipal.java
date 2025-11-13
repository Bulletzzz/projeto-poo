package ui;

import java.util.Scanner;
import servico.ServicoCliente;
import servico.ServicoPedido;
import servico.ServicoProduto;

public class MenuPrincipal {
    private final ServicoCliente servicoCliente;
    private final ServicoProduto servicoProduto;
    private final ServicoPedido servicoPedido;
    private final Scanner scanner = new Scanner(System.in);

    public MenuPrincipal(ServicoCliente servicoCliente, ServicoProduto servicoProduto, ServicoPedido servicoPedido) {
        this.servicoCliente = servicoCliente;
        this.servicoProduto = servicoProduto;
        this.servicoPedido = servicoPedido;
    }

    public void exibir() {
        int opcao;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Cadastrar Produto");
            System.out.println("3. Criar Pedido");
            System.out.println("4. Listar Clientes");
            System.out.println("5. Listar Produtos");
            System.out.println("6. Listar Pedidos");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    servicoCliente.cadastrar(scanner);
                    break;
                case 2:
                    servicoProduto.cadastrar(scanner);
                    break;
                case 3:
                    servicoPedido.criar(scanner);
                    break;
                case 4:
                    servicoCliente.listar();
                    break;
                case 5:
                    servicoProduto.listar();
                    break;
                case 6:
                    servicoPedido.listar();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
}