package br.com.cdb.controledespesas.core.domain.decorator;

import br.com.cdb.controledespesas.adapter.input.request.DespesaRequest;
import br.com.cdb.controledespesas.adapter.input.request.FiltroDespesasRequest;
import br.com.cdb.controledespesas.adapter.input.response.SomaDespesasResponse;
import br.com.cdb.controledespesas.core.domain.model.Despesa;
import br.com.cdb.controledespesas.port.input.DespesaInputPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DespesaUseCaseLoggerDecorator implements DespesaInputPort {

    private static final Logger log = LoggerFactory.getLogger(DespesaUseCaseLoggerDecorator.class);
    private final DespesaInputPort decorated;

    public DespesaUseCaseLoggerDecorator(DespesaInputPort decorated) {
        this.decorated = decorated;
    }

    @Override
    public Despesa salvarDespesa(Despesa despesa) {
        log.info("Salvando despesa: id={}, descricao={}, valor={}, usuarioId={}, categoriaId={}",
                despesa.getId(), despesa.getDescricao(), despesa.getValor(),
                despesa.getUsuarioId(), despesa.getCategoriaId());

        Despesa result = decorated.salvarDespesa(despesa);

        log.info("Despesa salva: id={}, descricao={}, valor={}",
                result.getId(), result.getDescricao(), result.getValor());

        return result;
    }

    @Override
    public List<Despesa> filtrarDespesas(FiltroDespesasRequest filtro) {
        log.info("Filtrando despesas: usuarioId={}, categoriaId={}, de={}, ate={}",
                filtro.getUsuarioId(), filtro.getCategoriaId(), filtro.getDe(), filtro.getAte());

        List<Despesa> result = decorated.filtrarDespesas(filtro);

        log.info("Total de despesas filtradas: {}", result.size());

        return result;
    }

    @Override
    public void deletarDespesaPorId(Long id, Long usuarioId) {
        log.info("Deletando despesa: id={}, usuarioId={}", id, usuarioId);
        decorated.deletarDespesaPorId(id, usuarioId);
        log.info("Despesa deletada com sucesso: id={}, usuarioId={}", id, usuarioId);
    }

    @Override
    public Despesa atualizarDespesa(Long id, Long usuarioId, DespesaRequest despesaRequest) {
        log.info("Atualizando despesa: id={}, usuarioId={}, descricao={}, valor={}",
                id, usuarioId, despesaRequest.getDescricao(), despesaRequest.getValor());

        Despesa result = decorated.atualizarDespesa(id, usuarioId, despesaRequest);

        log.info("Despesa atualizada: id={}, descricao={}, valor={}",
                result.getId(), result.getDescricao(), result.getValor());

        return result;
    }

    @Override
    public SomaDespesasResponse listarDespesasComTotal(FiltroDespesasRequest filtro) {
        log.info("Calculando soma total de despesas: usuarioId={}, categoriaId={}, de={}, ate={}",
                filtro.getUsuarioId(), filtro.getCategoriaId(), filtro.getDe(), filtro.getAte());

        SomaDespesasResponse result = decorated.listarDespesasComTotal(filtro);

        log.info("Soma total calculada: {}", result.getTotalGasto());

        return result;
    }
}
