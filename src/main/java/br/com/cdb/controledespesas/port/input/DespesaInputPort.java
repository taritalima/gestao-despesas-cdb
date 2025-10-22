package br.com.cdb.controledespesas.port.input;

import br.com.cdb.controledespesas.adapter.input.request.DespesaRequest;
import br.com.cdb.controledespesas.adapter.input.request.FiltroDespesasRequest;
import br.com.cdb.controledespesas.adapter.input.response.SomaDespesasResponse;
import br.com.cdb.controledespesas.core.domain.model.Despesa;

import java.util.List;

public interface DespesaInputPort {
    Despesa salvarDespesa(Despesa despesa);
    void deletarDespesaPorId(Long id, Long usuarioId);
    Despesa atualizarDespesa(Long id, Long usuarioId, DespesaRequest despesaRequest);
    List<Despesa> filtrarDespesas(FiltroDespesasRequest filtroDespesasRequest);
    SomaDespesasResponse listarDespesasComTotal(FiltroDespesasRequest filtro);

}
