package br.com.cdb.controledespesas.core.domain.usecase;

import br.com.cdb.controledespesas.adapter.input.request.DespesaRequest;
import br.com.cdb.controledespesas.adapter.input.request.FiltroDespesasRequest;
import br.com.cdb.controledespesas.adapter.input.request.SomaDespesasRequest;
import br.com.cdb.controledespesas.adapter.output.entity.CategoriaEntity;
import br.com.cdb.controledespesas.adapter.output.entity.DespesaEntity;
import br.com.cdb.controledespesas.core.domain.exception.BusinessRuleException;
import br.com.cdb.controledespesas.adapter.output.repository.CategoriaRepository;
import br.com.cdb.controledespesas.adapter.output.repository.DespesaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DespesaUseCase {

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional
    public DespesaEntity salvarDespesa(DespesaRequest despesaRequest) {

        CategoriaEntity categoria = categoriaRepository.findById(despesaRequest.getCategoriaId())
                .orElseThrow(() -> new BusinessRuleException("Categoria não encontrada"));

        DespesaEntity despesa = new DespesaEntity();
        despesa.setCategoria(categoria);
        despesa.setDescricao(despesaRequest.getDescricao());
        despesa.setValor(despesaRequest.getValor());
        despesa.setPagoEm(despesaRequest.getPagoEm());
        despesa.setUsuarioId(despesaRequest.getUsuarioId());

        return despesaRepository.save(despesa);
    }

    private DespesaRequest converterParaDTO(DespesaEntity despesaEntity) {
        DespesaRequest dto = new DespesaRequest();
        dto.setUsuarioId(despesaEntity.getUsuarioId());
        dto.setCategoriaId(despesaEntity.getCategoria().getId());
        dto.setDescricao(despesaEntity.getDescricao());
        dto.setValor(despesaEntity.getValor());
        dto.setPagoEm(despesaEntity.getPagoEm());
        dto.setCriadoEm(despesaEntity.getCriadoEm());
        return dto;
    }

    public SomaDespesasRequest filtrarDespesas(FiltroDespesasRequest filtroDespesasRequest) {
        CategoriaEntity categoria = null;
        if (filtroDespesasRequest.getCategoriaId() != null) {
            categoria = categoriaRepository.findById(filtroDespesasRequest.getCategoriaId())
                    .orElseThrow(() -> new BusinessRuleException("Categoria não encontrada"));
        }
        if (filtroDespesasRequest.getDe() != null && filtroDespesasRequest.getAte() != null
                && filtroDespesasRequest.getDe().isAfter(filtroDespesasRequest.getAte())) {
            throw new BusinessRuleException("A data de início não pode ser maior que a data final!");
        }
        List<DespesaEntity> despesaEntity = despesaRepository.findByUsuarioIdAndCategoriaAndPagoEmBetween(
                filtroDespesasRequest.getUsuarioId(),
                categoria,
                filtroDespesasRequest.getDe(),
                filtroDespesasRequest.getAte()
        );

        List<DespesaRequest> despesasRequest = despesaEntity.stream()
                .map(this::converterParaDTO)
                .toList();

        BigDecimal total = despesasRequest.stream()
                .map(DespesaRequest::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new SomaDespesasRequest(total,despesasRequest);

    }

    public void deletarDespesaPorId(Long id, Long usuarioId) {
        DespesaEntity despesa = despesaRepository.findByIdAndUsuarioId(id, usuarioId).orElseThrow(() ->
                new BusinessRuleException("Despesa não encontrada para o usuário"));
        despesaRepository.delete(despesa);
    }

    @Transactional
    public DespesaEntity atualizarDespesa(Long id, Long usuarioId, DespesaRequest despesaDTO) {
        DespesaEntity despesa = despesaRepository.findByIdAndUsuarioId(id, usuarioId).orElseThrow(() ->
                new BusinessRuleException("Despesa não encontrada para o usuário"));

        CategoriaEntity categoria = categoriaRepository.findById(despesaDTO.getCategoriaId())
                .orElseThrow(() -> new BusinessRuleException("Categoria não encontrada"));

        despesa.setDescricao(despesaDTO.getDescricao());
        despesa.setValor(despesaDTO.getValor());
        despesa.setPagoEm(despesaDTO.getPagoEm());
        despesa.setCategoria(categoria);

        return  despesaRepository.save(despesa);

    }
}
