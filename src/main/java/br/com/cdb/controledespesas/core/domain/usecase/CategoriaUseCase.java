package br.com.cdb.controledespesas.core.domain.usecase;


import br.com.cdb.controledespesas.adapter.input.request.CategoriaRequest;
import br.com.cdb.controledespesas.core.domain.exception.BusinessRuleException;
import br.com.cdb.controledespesas.core.domain.model.Categoria;
import br.com.cdb.controledespesas.port.input.CategoriaInputPort;
import br.com.cdb.controledespesas.port.output.CategoriaOutputPort;
import br.com.cdb.controledespesas.port.output.DespesaOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaUseCase implements CategoriaInputPort{

    @Autowired
    CategoriaOutputPort categoriaOutputPort;

    @Autowired
    DespesaOutputPort despesaOutputPort;

    public Categoria salvarCategoria(CategoriaRequest categoriaRequest){

        if(categoriaOutputPort.buscarPorName(categoriaRequest.getNome()).isPresent()){
            throw new BusinessRuleException("Já existe Categoria com esse nome!");
        }
        Categoria categoria = new Categoria();
        categoria.setNome((categoriaRequest.getNome()));

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
