package br.com.cdb.controledespesas.service;


import br.com.cdb.controledespesas.dto.CategoriaDTO;
import br.com.cdb.controledespesas.entity.Categoria;
import br.com.cdb.controledespesas.exception.BusinessRuleException;
import br.com.cdb.controledespesas.repository.CategoriaRepository;
import br.com.cdb.controledespesas.repository.DespesaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    DespesaRepository despesaRepository;

    @Transactional
    public Categoria salvarCategoria(CategoriaDTO categoriaDTO){

        if(categoriaRepository.existsByNome(categoriaDTO.getNome())){
            throw new BusinessRuleException("Já existe Categoria com esse nome!");
        }
        Categoria categoria = new Categoria();
        categoria.setNome((categoriaDTO.getNome()));

        return categoriaRepository.save(categoria);

    }
    @Transactional
    public void deletarCategoria(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("Categoria não encontrada"));

        if (despesaRepository.existsByCategoria(categoria)) {
            throw new BusinessRuleException("Não é possível remover a categoria, existem despesas vinculadas.");
        }

        categoriaRepository.delete(categoria);
    }
}
