package repositorio.impl;

import model.Cliente;
import repositorio.Repositorio;
import exceptions.ValidacaoException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class RepositorioClientes implements Repositorio<Cliente> {
    private final Map<Integer, Cliente> clientes = new HashMap<>();
    private final AtomicInteger proximoId = new AtomicInteger(1);

    @Override
    public void adicionar(Cliente cliente) {
        clientes.put(cliente.obterId(), cliente);
    }

    @Override
    public Cliente encontrarPorId(int id) {
        return clientes.get(id);
    }

    @Override
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes.values());
    }

    @Override
    public int obterProximoId() {
        return proximoId.getAndIncrement();
    }

    @Override
    public void salvarEmArquivo(String arquivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
            for (Cliente c : clientes.values()) {
                writer.write(c.toCSV());
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
                String email = partes[2];
                Cliente c = Cliente.criar(id, nome, email);
                adicionar(c);
                proximoId.set(Math.max(proximoId.get(), id + 1));
            }
        }
    }
}