package br.com.cdb.controledespesas.core.domain;

import br.com.cdb.controledespesas.core.domain.exception.BusinessRuleException;
import br.com.cdb.controledespesas.core.domain.model.Categoria;
import br.com.cdb.controledespesas.port.input.CategoriaInputPort;
import br.com.cdb.controledespesas.port.output.CategoriaOutputPort;
import br.com.cdb.controledespesas.port.output.DespesaOutputPort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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

    public Categoria buscarCategoriaId(Long id){
        Categoria categoria = categoriaOutputPort.buscarPorId(id)
                .orElseThrow(() -> {
            log.warn("Categoria não encontrada com id : {}",id);
            return new BusinessRuleException("Categoria não encontrada");
        });
        log.info("Categoria encontrada: {} (id={})", categoria.getNome(), categoria.getId());
        return categoria;
    }

    public List<Categoria> buscarTodasCategorias(){
        List<Categoria> categorias = categoriaOutputPort.buscarTodasCategoria();
        if (categorias.isEmpty()) {
            log.warn("Nenhuma categoria encontrada.");
            throw new BusinessRuleException("Não existem categorias cadastradas.");
        }

        log.info("Foram encontradas {} categorias", categorias.size());
        return categorias;
    }
}
