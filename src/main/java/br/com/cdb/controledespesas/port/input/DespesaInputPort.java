package br.com.cdb.controledespesas.port.input;

import br.com.cdb.controledespesas.adapter.input.request.DespesaRequest;
import br.com.cdb.controledespesas.adapter.input.request.FiltroDespesasRequest;
import br.com.cdb.controledespesas.core.domain.model.Despesa;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DespesaInputPort {
    Despesa salvarDespesa(DespesaRequest despesaRequest);
    void deletarDespesaPorId(Long id, Long usuarioId);
    Despesa atualizarDespesa(Long id, Long usuarioId, DespesaRequest despesaRequest);
    List<Despesa> filtrarDespesas(FiltroDespesasRequest filtroDespesasRequest);

    /*Optional<Despesa> buscarPorIdEUsuario(Long id, Long usuarioId);*/
}
