package br.com.cdb.controledespesas.port.input;

import br.com.cdb.controledespesas.adapter.output.entity.DespesaEntity;

import java.util.List;

public interface DespesaInputPort {
    DespesaEntity salvarDespesa(DespesaEntity despesa);
    void deletarDespesaPorId(Long id, Long usuarioId);
    DespesaEntity atualizarDespesa(Long id, Long usuarioId, DespesaEntity despesa);
    List<DespesaEntity> filtrarDespesas(Long usuarioId, Long categoriaId, String de, String ate);
}
