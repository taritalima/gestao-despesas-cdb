package br.com.cdb.controledespesas.port.input;
import br.com.cdb.controledespesas.adapter.input.request.CategoriaRequest;
import br.com.cdb.controledespesas.core.domain.model.Categoria;
import java.util.Optional;

public interface CategoriaInputPort {
    Categoria salvarCategoria(CategoriaRequest categoriaRequest);
    void deletarCategoria(Long id);
}
