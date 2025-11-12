package repositorio;

import java.util.List;

public interface Repositorio<T> {
    void adicionar(T entidade);
    T buscarPorId(long id);
    List<T> listarTodos();
    long proximoId();
}