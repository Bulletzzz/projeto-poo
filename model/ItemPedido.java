package model;

import exceptions.ValidacaoException;

public class ItemPedido {
    private final Produto produto;
    private final int quantidade;

    private ItemPedido(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public static ItemPedido criar(Produto produto, int quantidade) throws ValidacaoException {
        if (quantidade <= 0) {
            throw new ValidacaoException("Quantidade deve ser positiva.");
        }
        return new ItemPedido(produto, quantidade);
    }

    public double calcularValor() {
        return produto.obterPreco() * quantidade;
    }

    public void exibirInformacoes() {
        System.out.println("Produto: " + produto.obterNome() + ", Quantidade: " + quantidade + ", Subtotal: " + calcularValor());
    }

    public String toCSV() {
        return produto.obterId() + ":" + quantidade;
    }
}