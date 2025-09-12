package br.com.cdb.controledespesas.port.output;

import br.com.cdb.controledespesas.core.domain.model.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaOutputPort {
    Categoria salvarCategoria(Categoria categoria);
    void deletarCategoria(Long id);
    Optional<Categoria> buscarPorId(Long id);
    Optional<Categoria> buscarPorName(String nome);
    List<Categoria> buscarTodasCategoria();
}
