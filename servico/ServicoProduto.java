package servico;

import enums.CategoriaProduto;
import exceptions.ValidacaoException;
import java.util.Scanner;
import model.Produto;
import repositorio.Repositorio;

public class ServicoProduto {
    private final Repositorio<Produto> repositorio;

    public ServicoProduto(Repositorio<Produto> repositorio) {
        this.repositorio = repositorio;
    }

    public void cadastrar(Scanner scanner) {
        try {
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("Preço: ");
            double preco = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Categoria (ALIMENTOS, ELETRONICOS, LIVROS): ");
            CategoriaProduto cat = CategoriaProduto.valueOf(scanner.nextLine().toUpperCase());
            int id = repositorio.obterProximoId();
            Produto produto = Produto.criar(id, nome, preco, cat);
            repositorio.adicionar(produto);
            System.out.println("Produto cadastrado.");
        } catch (ValidacaoException | IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void listar() {
        for (Produto p : repositorio.listarTodos()) {
            p.exibirInformacoes();
        }
    }
}