package br.com.cdb.controledespesas.core.domain.usecase;

import br.com.cdb.controledespesas.core.domain.exception.BusinessRuleException;
import br.com.cdb.controledespesas.core.domain.model.Categoria;
import br.com.cdb.controledespesas.port.input.CategoriaInputPort;
import br.com.cdb.controledespesas.port.output.CategoriaOutputPort;
import br.com.cdb.controledespesas.port.output.DespesaOutputPort;


public class CategoriaUseCase implements CategoriaInputPort{

    private final CategoriaOutputPort categoriaOutputPort;
    private final DespesaOutputPort despesaOutputPort;


    public CategoriaUseCase(CategoriaOutputPort categoriaOutputPort, DespesaOutputPort despesaOutputPort) {
        this.categoriaOutputPort = categoriaOutputPort;
        this.despesaOutputPort = despesaOutputPort;
    }


    public Categoria salvarCategoria(Categoria categoria){

        if(categoriaOutputPort.buscarPorName(categoria.getNome()).isPresent()){
            throw new BusinessRuleException("Já existe Categoria com esse nome!");
        }

        return categoriaOutputPort.salvarCategoria(categoria);

    }
    public void deletarCategoria(Long id) {
        Categoria categoria = categoriaOutputPort.buscarPorId(id)
                .orElseThrow(() -> new BusinessRuleException("Categoria não encontrada"));

        if (despesaOutputPort.existsByCategoria(categoria.getId())) {
            throw new BusinessRuleException("Não é possível remover a categoria, existem despesas vinculadas.");
        }

        categoriaOutputPort.deletarCategoria(categoria.getId());
    }
}
