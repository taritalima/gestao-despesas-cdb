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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DespesaUseCase implements DespesaInputPort {

    private final CategoriaOutputPort categoriaOutputPort;
    private final DespesaOutputPort despesaOutputPort;
    private final DespesaMapper despesaMapper;
    private static final Logger log = LoggerFactory.getLogger(DespesaUseCase.class);


    public DespesaUseCase(CategoriaOutputPort categoriaOutputPort, DespesaOutputPort despesaOutputPort, DespesaMapper despesaMapper) {
        this.categoriaOutputPort = categoriaOutputPort;
        this.despesaOutputPort = despesaOutputPort;
        this.despesaMapper = despesaMapper;
    }

    public Despesa salvarDespesa(Despesa despesa) {
        try {
            Categoria categoria = categoriaOutputPort.buscarPorId(despesa.getCategoriaId())
                    .orElseThrow(() -> {
                        log.error("Categoria não encontrada: {}", despesa.getCategoriaId());
                        return new BusinessRuleException("Categoria não encontrada");
                    });

            despesa.setCategoriaId(categoria.getId());
            Despesa salva = despesaOutputPort.salvarDespesa(despesa);

            log.info("Despesa salva com sucesso: {}", salva);
            return salva;

        } catch (Exception e) {
            log.error("Erro ao salvar despesa: {}", despesa, e);
            throw e;
        }
    }

    public List<Despesa> filtrarDespesas(FiltroDespesasRequest filtroDespesasRequest) {
        try {
            Categoria categoria = null;
            if (filtroDespesasRequest.getCategoriaId() != null) {
                categoria = categoriaOutputPort.buscarPorId(filtroDespesasRequest.getCategoriaId())
                        .orElseThrow(() -> {
                            log.warn("Categoria não encontrada: {}", filtroDespesasRequest.getCategoriaId());
                            return new BusinessRuleException("Categoria não encontrada");
                        });
            }
            if (filtroDespesasRequest.getDe() != null && filtroDespesasRequest.getAte() != null
                    && filtroDespesasRequest.getDe().isAfter(filtroDespesasRequest.getAte())) {
                log.warn("Data de início maior que data final: de={} ate={}",
                        filtroDespesasRequest.getDe(), filtroDespesasRequest.getAte());
                throw new BusinessRuleException("A data de início não pode ser maior que a data final!");
            }

            List<Despesa> despesas = despesaOutputPort.filtrarDespesas(
                    filtroDespesasRequest.getUsuarioId(),
                    categoria != null ? categoria.getId() : null,
                    filtroDespesasRequest.getDe(),
                    filtroDespesasRequest.getAte()
            );
            log.info("Filtradas {} despesas para o usuário {}", despesas.size(), filtroDespesasRequest.getUsuarioId());
            return despesas;
        } catch (BusinessRuleException e) {
            log.warn("Regra de negócio violada ao filtrar informações despesa: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao filtrar informações despesa");
            throw e;
        }
    }

    public void deletarDespesaPorId(Long id, Long usuarioId) {
        try {
            despesaOutputPort.buscarPorIdEUsuario(id, usuarioId)
                    .orElseThrow(() -> {
                        log.warn("Despesa não encontrada para o usuário={} com id={}", usuarioId, id);
                        return new BusinessRuleException("Despesa não encontrada para o usuário");
                    });

            despesaOutputPort.deletarDespesaPorId(id, usuarioId);
            log.info("Despesa deletada com sucesso: id={} usuarioId={}", id, usuarioId);

        } catch (BusinessRuleException e) {
            log.warn("Regra de negócio violada ao deletar despesa: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao deletar despesa: id={} usuarioId={}", id, usuarioId, e);
            throw e;
        }
    }

    public Despesa atualizarDespesa(Long id, Long usuarioId, DespesaRequest despesaRequest) {
        try {
            Despesa despesaExistente = despesaOutputPort.buscarPorIdEUsuario(id, usuarioId)
                    .orElseThrow(() -> {
                        log.warn("Despesa não encontrada para o usuário={} com id={}", usuarioId, id);
                        return new BusinessRuleException("Despesa não encontrada para o usuário");
                    });

            Categoria categoria = categoriaOutputPort.buscarPorId(despesaRequest.getCategoriaId())
                    .orElseThrow(() -> {
                        log.warn("Categoria não encontrada: {}", despesaRequest.getCategoriaId());
                        return new BusinessRuleException("Categoria não encontrada");
                    });

            despesaExistente.setDescricao(despesaRequest.getDescricao());
            despesaExistente.setValor(despesaRequest.getValor());
            despesaExistente.setCategoriaId(categoria.getId());
            despesaExistente.setPagoEm(despesaRequest.getPagoEm());

            Despesa atualizada = despesaOutputPort.atualizarDespesa(despesaExistente);

            log.info("Despesa atualizada com sucesso: id={} usuarioId={}", id, usuarioId);
            return atualizada;

        } catch (BusinessRuleException e) {
            log.warn("Regra de negócio violada ao atualizar despesa: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao atualizar despesa id={} usuarioId={}", id, usuarioId, e);
            throw e;
        }
    }

    public SomaDespesasResponse listarDespesasComTotal(FiltroDespesasRequest filtro) {
        List<Despesa> despesas = filtrarDespesas(filtro);

        if (despesas.isEmpty()) {
            log.warn("Nenhuma despesa encontrada para o usuário {}", filtro.getUsuarioId());
            throw new BusinessRuleException("Nenhuma despesa encontrada para o usuário");
        }

        BigDecimal total = despesas.stream()
                .map(Despesa::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<DespesaResponse> despesasResponse = despesas.stream()
                .map(despesaMapper::toResponse)
                .toList();

        return new SomaDespesasResponse(total, despesasResponse);
    }
}
