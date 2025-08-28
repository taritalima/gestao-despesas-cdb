package br.com.cdb.controledespesas.port.output;

import br.com.cdb.controledespesas.core.domain.model.Despesa;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DespesaOutputPort {
    Despesa salvarDespesa(Despesa despesa);
    void deletarDespesaPorId(Long id, Long usuarioId);
    Despesa atualizarDespesa(Despesa despesa);
    List<Despesa> filtrarDespesas(Long usuarioId, Long categoriaId, LocalDate de, LocalDate ate);

    Optional<Despesa> buscarPorIdEUsuario(Long id, Long usuarioId);
    boolean existsByCategoria(Long categoriaId);
}
