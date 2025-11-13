package model;

import enums.CategoriaProduto;
import exceptions.ValidacaoException;

public class Produto extends Entidade {
    private final String nome;
    private final double preco;
    private final CategoriaProduto categoria;

    private Produto(int id, String nome, double preco, CategoriaProduto categoria) {
        super(id);
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
    }

    public static Produto criar(int id, String nome, double preco, CategoriaProduto categoria) throws ValidacaoException {
        if (nome == null || nome.isEmpty()) {
            throw new ValidacaoException("Nome não pode ser vazio.");
        }
        if (preco <= 0) {
            throw new ValidacaoException("Preço deve ser positivo.");
        }
        if (categoria == null) {
            throw new ValidacaoException("Categoria é obrigatória.");
        }
        return new Produto(id, nome, preco, categoria);
    }

    public String obterNome() {
        return nome;
    }

    public double obterPreco() {
        return preco;
    }

    public CategoriaProduto obterCategoria() {
        return categoria;
    }

    public void exibirInformacoes() {
        System.out.println("ID: " + id + ", Nome: " + nome + ", Preço: " + preco + ", Categoria: " + categoria);
    }

    @Override
    public String toCSV() {
        return id + "," + nome + "," + preco + "," + categoria.name();
    }

    @Override
    public void fromCSV(String linha) {
    }
}