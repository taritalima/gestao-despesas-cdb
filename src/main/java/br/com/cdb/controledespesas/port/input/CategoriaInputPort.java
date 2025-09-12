package br.com.cdb.controledespesas.port.input;
import br.com.cdb.controledespesas.core.domain.model.Categoria;

import java.util.List;

public interface CategoriaInputPort {
    Categoria salvarCategoria(Categoria categoria);
    void deletarCategoria(Long id);
    Categoria buscarCategoriaId(Long id);
    List<Categoria> buscarTodasCategorias();
}
