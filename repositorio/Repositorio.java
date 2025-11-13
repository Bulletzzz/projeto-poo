package repositorio;

import exceptions.ValidacaoException;
import java.io.IOException;
import java.util.List;

public interface Repositorio<T> {
    void adicionar(T entidade);
    T encontrarPorId(int id);
    List<T> listarTodos();
    int obterProximoId();
    void salvarEmArquivo(String arquivo) throws IOException;
    void carregarDeArquivo(String arquivo) throws IOException, ValidacaoException;
}