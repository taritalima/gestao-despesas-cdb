package br.com.cdb.controledespesas.core.domain.usecase;

import br.com.cdb.controledespesas.core.domain.exception.BusinessRuleException;
import br.com.cdb.controledespesas.core.domain.model.Categoria;
import br.com.cdb.controledespesas.port.input.CategoriaInputPort;
import br.com.cdb.controledespesas.port.output.CategoriaOutputPort;
import br.com.cdb.controledespesas.port.output.DespesaOutputPort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CategoriaUseCase implements CategoriaInputPort{

    private final CategoriaOutputPort categoriaOutputPort;
    private final DespesaOutputPort despesaOutputPort;
    private static final Logger log = LoggerFactory.getLogger(CategoriaUseCase.class);


    public CategoriaUseCase(CategoriaOutputPort categoriaOutputPort, DespesaOutputPort despesaOutputPort) {
        this.categoriaOutputPort = categoriaOutputPort;
        this.despesaOutputPort = despesaOutputPort;
    }


    public Categoria salvarCategoria(Categoria categoria){

        if(categoriaOutputPort.buscarPorName(categoria.getNome()).isPresent()){
            log.warn("Já existe categoria com esse nome: {}", categoria.getNome());
            throw new BusinessRuleException("Já existe Categoria com esse nome!");
        }

        Categoria salva = categoriaOutputPort.salvarCategoria(categoria);
        log.info("Categoria '{}' salva com sucesso", salva.getNome());
        return salva;

    }
    public void deletarCategoria(Long id) {
        Categoria categoria = categoriaOutputPort.buscarPorId(id)
                .orElseThrow(() -> {
                    log.warn("Categoria não encontrada com id: {}", id);
                    return new BusinessRuleException("Categoria não encontrada");
                });

        if (despesaOutputPort.existsByCategoria(categoria.getId())) {
            log.warn("Não é possível remover a categoria, existem despesas vinculadas na categoria: {}, com id {}", categoria.getNome(), categoria.getId());
            throw new BusinessRuleException("Não é possível remover a categoria, existem despesas vinculadas.");
        }
        categoriaOutputPort.deletarCategoria(categoria.getId());
        log.info("Categoria deletada com sucesso: {} (id={})", categoria.getNome(), categoria.getId());
    }
}
