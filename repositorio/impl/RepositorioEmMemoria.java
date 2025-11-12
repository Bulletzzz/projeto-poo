package repositorio.impl;

import model.Entidade;
import repositorio.Repositorio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class RepositorioEmMemoria<T extends Entidade> implements Repositorio<T> {
    private final Map<Long, T> dados = new HashMap<>();
    private final AtomicLong contador = new AtomicLong(1);

    @Override
    public synchronized void adicionar(T entidade) {
        dados.put(entidade.getId(), entidade);
    }

    @Override
    public synchronized T buscarPorId(long id) {
        return dados.get(id);
    }

    @Override
    public synchronized List<T> listarTodos() {
        return new ArrayList<>(dados.values());
    }

    @Override
    public long proximoId() {
        return contador.getAndIncrement();
    }
}