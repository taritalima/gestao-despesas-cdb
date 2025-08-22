package br.com.cdb.controledespesas.service;

import br.com.cdb.controledespesas.dto.DespesaDTO;
import br.com.cdb.controledespesas.dto.FiltroDespesasDTO;
import br.com.cdb.controledespesas.dto.SomaDespesasCategoriaDTO;
import br.com.cdb.controledespesas.entity.Categoria;
import br.com.cdb.controledespesas.entity.Despesa;
import br.com.cdb.controledespesas.exception.BusinessRuleException;
import br.com.cdb.controledespesas.repository.CategoriaRepository;
import br.com.cdb.controledespesas.repository.DespesaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DespesaService {

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional
    public Despesa salvarDespesa(DespesaDTO despesaDTO) {

        Categoria categoria = categoriaRepository.findById(despesaDTO.getCategoriaId())
                .orElseThrow(() -> new BusinessRuleException("Categoria não encontrada"));

        Despesa despesa = new Despesa();
        despesa.setCategoria(categoria);
        despesa.setDescricao(despesaDTO.getDescricao());
        despesa.setValor(despesaDTO.getValor());
        despesa.setPagoEm(despesaDTO.getPagoEm());
        despesa.setUsuarioId(despesaDTO.getUsuarioId());

        return despesaRepository.save(despesa);
    }

    private DespesaDTO converterParaDTO(Despesa despesa) {
        DespesaDTO dto = new DespesaDTO();
        dto.setUsuarioId(despesa.getUsuarioId());
        dto.setCategoriaId(despesa.getCategoria().getId());
        dto.setDescricao(despesa.getDescricao());
        dto.setValor(despesa.getValor());
        dto.setPagoEm(despesa.getPagoEm());
        dto.setCriadoEm(despesa.getCriadoEm());
        return dto;
    }

    public SomaDespesasCategoriaDTO filtrarDespesas(FiltroDespesasDTO filtroDespesasDTO) {
        Categoria categoria = null;
        if (filtroDespesasDTO.getCategoriaId() != null) {
            categoria = categoriaRepository.findById(filtroDespesasDTO.getCategoriaId())
                    .orElseThrow(() -> new BusinessRuleException("Categoria não encontrada"));
        }
        if (filtroDespesasDTO.getDe() != null && filtroDespesasDTO.getAte() != null
                && filtroDespesasDTO.getDe().isAfter(filtroDespesasDTO.getAte())) {
            throw new BusinessRuleException("A data de início não pode ser maior que a data final!");
        }
        List<Despesa> despesas = despesaRepository.findByUsuarioIdAndCategoriaAndPagoEmBetween(
                filtroDespesasDTO.getUsuarioId(),
                categoria,
                filtroDespesasDTO.getDe(),
                filtroDespesasDTO.getAte()
        );

        List<DespesaDTO> despesasDTO = despesas.stream()
                .map(this::converterParaDTO)
                .toList();

        BigDecimal total = despesasDTO.stream()
                .map(DespesaDTO::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new SomaDespesasCategoriaDTO(total,despesasDTO);

    }

    @Transactional
    public void deletarDespesaPorId(Long id, Long usuarioId) {
        Despesa despesa = despesaRepository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new BusinessRuleException(
                        "Despesa não encontrada para o usuário " + usuarioId));

        despesaRepository.delete(despesa);
    }

    @Transactional
    public Despesa atualizarDespesa(Long id, Long usuarioId, DespesaDTO despesaDTO) {
        Despesa despesa = despesaRepository.findByIdAndUsuarioId(id, usuarioId).orElseThrow(() ->
                new BusinessRuleException("Despesa não encontrada para o usuário"));

        Categoria categoria = categoriaRepository.findById(despesaDTO.getCategoriaId())
                .orElseThrow(() -> new BusinessRuleException("Categoria não encontrada"));

        despesa.setDescricao(despesaDTO.getDescricao());
        despesa.setValor(despesaDTO.getValor());
        despesa.setPagoEm(despesaDTO.getPagoEm());
        despesa.setCategoria(categoria);

        return  despesaRepository.save(despesa);

    }
}
