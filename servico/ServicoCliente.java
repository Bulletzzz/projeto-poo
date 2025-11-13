package servico;

import exceptions.ValidacaoException;
import java.util.Scanner;
import model.Cliente;
import repositorio.Repositorio;

public class ServicoCliente {
    private final Repositorio<Cliente> repositorio;

    public ServicoCliente(Repositorio<Cliente> repositorio) {
        this.repositorio = repositorio;
    }

    public void cadastrar(Scanner scanner) {
        try {
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("E-mail: ");
            String email = scanner.nextLine();
            int id = repositorio.obterProximoId();
            Cliente cliente = Cliente.criar(id, nome, email);
            repositorio.adicionar(cliente);
            System.out.println("Cliente cadastrado.");
        } catch (ValidacaoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void listar() {
        for (Cliente c : repositorio.listarTodos()) {
            c.exibirInformacoes();
        }
    }
}