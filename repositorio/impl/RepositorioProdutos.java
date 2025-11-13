package repositorio.impl;

import enums.CategoriaProduto;
import exceptions.ValidacaoException;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import model.Produto;
import repositorio.Repositorio;

public class RepositorioProdutos implements Repositorio<Produto> {
    private final Map<Integer, Produto> produtos = new HashMap<>();
    private final AtomicInteger proximoId = new AtomicInteger(1);

    @Override
    public void adicionar(Produto produto) {
        produtos.put(produto.obterId(), produto);
    }

    @Override
    public Produto encontrarPorId(int id) {
        return produtos.get(id);
    }

    @Override
    public List<Produto> listarTodos() {
        return new ArrayList<>(produtos.values());
    }

    @Override
    public int obterProximoId() {
        return proximoId.getAndIncrement();
    }

    @Override
    public void salvarEmArquivo(String arquivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
            for (Produto p : produtos.values()) {
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
                String nome = partes[1];
                double preco = Double.parseDouble(partes[2]);
                CategoriaProduto cat = CategoriaProduto.valueOf(partes[3]);
                Produto p = Produto.criar(id, nome, preco, cat);
                adicionar(p);
                proximoId.set(Math.max(proximoId.get(), id + 1));
            }
        }
    }
}