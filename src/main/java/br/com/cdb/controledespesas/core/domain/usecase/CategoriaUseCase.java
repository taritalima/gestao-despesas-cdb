package br.com.cdb.controledespesas.core.domain.usecase;


import br.com.cdb.controledespesas.adapter.input.request.CategoriaRequest;
import br.com.cdb.controledespesas.adapter.output.entity.CategoriaEntity;
import br.com.cdb.controledespesas.core.domain.exception.BusinessRuleException;
import br.com.cdb.controledespesas.adapter.output.repository.CategoriaRepository;
import br.com.cdb.controledespesas.adapter.output.repository.DespesaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaUseCase {

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    DespesaRepository despesaRepository;

    @Transactional
    public CategoriaEntity salvarCategoria(CategoriaRequest categoriaDTO){

        if(categoriaRepository.existsByNome(categoriaDTO.getNome())){
            throw new BusinessRuleException("Já existe Categoria com esse nome!");
        }
        CategoriaEntity categoria = new CategoriaEntity();
        categoria.setNome((categoriaDTO.getNome()));

        return categoriaRepository.save(categoria);

    }
    @Transactional
    public void deletarCategoria(Long id) {
        CategoriaEntity categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("Categoria não encontrada"));

        if (despesaRepository.existsByCategoria(categoria)) {
            throw new BusinessRuleException("Não é possível remover a categoria, existem despesas vinculadas.");
        }

        categoriaRepository.delete(categoria);
    }
}
