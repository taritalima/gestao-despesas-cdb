package br.com.cdb.controledespesas.port.input;

import br.com.cdb.controledespesas.adapter.output.entity.CategoriaEntity;

public interface CategoriaInputPort {
    CategoriaEntity salvarCategoria(CategoriaEntity categoria);
    void deletarCategoria(Long id);
}
