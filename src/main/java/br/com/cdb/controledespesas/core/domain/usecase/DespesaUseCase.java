package br.com.cdb.controledespesas.core.domain.usecase;

import br.com.cdb.controledespesas.adapter.input.mapper.DespesaMapper;
import br.com.cdb.controledespesas.adapter.input.request.DespesaRequest;
import br.com.cdb.controledespesas.adapter.input.request.FiltroDespesasRequest;
import br.com.cdb.controledespesas.adapter.input.response.DespesaResponse;
import br.com.cdb.controledespesas.adapter.input.response.SomaDespesasResponse;
import br.com.cdb.controledespesas.core.domain.exception.BusinessRuleException;
import br.com.cdb.controledespesas.core.domain.model.Categoria;
import br.com.cdb.controledespesas.core.domain.model.Despesa;
import br.com.cdb.controledespesas.port.input.DespesaInputPort;
import br.com.cdb.controledespesas.port.output.CategoriaOutputPort;
import br.com.cdb.controledespesas.port.output.DespesaOutputPort;

import java.math.BigDecimal;
import java.util.List;

public class DespesaUseCase implements DespesaInputPort {

    private final CategoriaOutputPort categoriaOutputPort;
    private final DespesaOutputPort despesaOutputPort;
    private final DespesaMapper despesaMapper;

    public DespesaUseCase(CategoriaOutputPort categoriaOutputPort, DespesaOutputPort despesaOutputPort, DespesaMapper despesaMapper) {
        this.categoriaOutputPort = categoriaOutputPort;
        this.despesaOutputPort = despesaOutputPort;
        this.despesaMapper = despesaMapper;
    }

    public Despesa salvarDespesa(Despesa despesa) {
        Categoria categoria = categoriaOutputPort.buscarPorId(despesa.getCategoriaId())
                .orElseThrow(() -> new BusinessRuleException("Categoria não encontrada"));

        despesa.setCategoriaId(categoria.getId());
        return despesaOutputPort.salvarDespesa(despesa);
    }


    public List<Despesa> filtrarDespesas(FiltroDespesasRequest filtroDespesasRequest) {
        Categoria categoria = null;
        if (filtroDespesasRequest.getCategoriaId() != null) {
            categoria = categoriaOutputPort.buscarPorId(filtroDespesasRequest.getCategoriaId())
                    .orElseThrow(() -> new BusinessRuleException("Categoria não encontrada"));
        }
        if (filtroDespesasRequest.getDe() != null && filtroDespesasRequest.getAte() != null
                && filtroDespesasRequest.getDe().isAfter(filtroDespesasRequest.getAte())) {
            throw new BusinessRuleException("A data de início não pode ser maior que a data final!");
        }
        return despesaOutputPort.filtrarDespesas(
                filtroDespesasRequest.getUsuarioId(),
                categoria != null ? categoria.getId() : null,
                filtroDespesasRequest.getDe(),
                filtroDespesasRequest.getAte()
        );
    }

    public void deletarDespesaPorId(Long id, Long usuarioId) {
        despesaOutputPort.buscarPorIdEUsuario(id, usuarioId)
                .orElseThrow(() -> new BusinessRuleException("Despesa não encontrada para o usuário"));

        despesaOutputPort.deletarDespesaPorId(id, usuarioId);
    }

    public Despesa atualizarDespesa(Long id, Long usuarioId, DespesaRequest despesaRequest) {
        Despesa despesaExistente = despesaOutputPort.buscarPorIdEUsuario(id, usuarioId)
                .orElseThrow(() -> new BusinessRuleException("Despesa não encontrada para o usuário"));

        Categoria categoria = categoriaOutputPort.buscarPorId(despesaRequest.getCategoriaId())
                .orElseThrow(() -> new BusinessRuleException("Categoria não encontrada"));

        despesaExistente.setDescricao(despesaRequest.getDescricao());
        despesaExistente.setValor(despesaRequest.getValor());
        despesaExistente.setCategoriaId(categoria.getId());
        despesaExistente.setPagoEm(despesaRequest.getPagoEm());

        return despesaOutputPort.atualizarDespesa(despesaExistente);
    }

    public SomaDespesasResponse listarDespesasComTotal(FiltroDespesasRequest filtro) {
        List<Despesa> despesas = filtrarDespesas(filtro);

        BigDecimal total = despesas.stream()
                .map(Despesa::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<DespesaResponse> despesasResponse = despesas.stream()
                .map(despesaMapper::toResponse)
                .toList();

        return new SomaDespesasResponse(total, despesasResponse);
    }
}
