import exceptions.ValidacaoException;
import fila.FilaPedidos;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;
import model.Cliente;
import model.Pedido;
import model.Produto;
import processador.ProcessadorPedidos;
import repositorio.Repositorio;
import repositorio.impl.RepositorioClientes;
import repositorio.impl.RepositorioPedidos;
import repositorio.impl.RepositorioProdutos;
import servico.ServicoCliente;
import servico.ServicoPedido;
import servico.ServicoProduto;
import ui.MenuPrincipal;

public class Main {
    private static final String ARQUIVO_CLIENTES = "clientes.csv";
    private static final String ARQUIVO_PRODUTOS = "produtos.csv";
    private static final String ARQUIVO_PEDIDOS = "pedidos.csv";

    public static void main(String[] args) {
        Repositorio<Cliente> repoClientes = new RepositorioClientes();
        Repositorio<Produto> repoProdutos = new RepositorioProdutos();
        Repositorio<Pedido> repoPedidos = new RepositorioPedidos(repoClientes, repoProdutos);

        carregarDados(repoClientes, repoProdutos, repoPedidos);

        FilaPedidos fila = new FilaPedidos();
        ProcessadorPedidos processador = new ProcessadorPedidos(fila);
        processador.start();

        Locale.setDefault(new Locale("pt", "BR"));
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(new Locale("pt", "BR"));
        
        ServicoCliente servicoCliente = new ServicoCliente(repoClientes);
        ServicoProduto servicoProduto = new ServicoProduto(repoProdutos);
        ServicoPedido servicoPedido = new ServicoPedido(repoPedidos, repoClientes, repoProdutos, fila);

        MenuPrincipal menu = new MenuPrincipal(servicoCliente, servicoProduto, servicoPedido);
        menu.exibir();

        processador.parar();
        salvarDados(repoClientes, repoProdutos, repoPedidos);
    }

    private static void carregarDados(Repositorio<Cliente> repoClientes, Repositorio<Produto> repoProdutos, Repositorio<Pedido> repoPedidos) {
        try {
            repoClientes.carregarDeArquivo(ARQUIVO_CLIENTES);
            repoProdutos.carregarDeArquivo(ARQUIVO_PRODUTOS);
            repoPedidos.carregarDeArquivo(ARQUIVO_PEDIDOS);
        } catch (IOException | ValidacaoException e) {
            System.out.println("Erro ao carregar dados: " + e.getMessage());
        }
    }

    private static void salvarDados(Repositorio<Cliente> repoClientes, Repositorio<Produto> repoProdutos, Repositorio<Pedido> repoPedidos) {
        try {
            repoClientes.salvarEmArquivo(ARQUIVO_CLIENTES);
            repoProdutos.salvarEmArquivo(ARQUIVO_PRODUTOS);
            repoPedidos.salvarEmArquivo(ARQUIVO_PEDIDOS);
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }
}