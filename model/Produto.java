package model;

import enums.CategoriaProduto;
import exceptions.ValidacaoException;

public final class Produto extends Entidade {
    private final String nome;
    private final double preco;
    private final CategoriaProduto categoria;

    private Produto(long id, String nome, double preco, CategoriaProduto categoria) {
        super(id);
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
    }

    public static Produto criar(long id, String nome, double preco, CategoriaProduto categoria) throws ValidacaoException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new ValidacaoException("Nome do produto é obrigatório.");
        }
        if (preco <= 0) {
            throw new ValidacaoException("Preço deve ser positivo.");
        }
        if (categoria == null) {
            throw new ValidacaoException("Categoria é obrigatória.");
        }
        return new Produto(id, nome.trim(), preco, categoria);
    }

    public String getNome() { return nome; }
    public double getPreco() { return preco; }
    public CategoriaProduto getCategoria() { return categoria; }
}