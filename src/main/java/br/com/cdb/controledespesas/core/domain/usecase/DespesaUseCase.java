package br.com.cdb.controledespesas.core.domain.usecase;

import br.com.cdb.controledespesas.adapter.input.mapper.DespesaMapper;
import br.com.cdb.controledespesas.adapter.input.request.DespesaRequest;
import br.com.cdb.controledespesas.adapter.input.request.FiltroDespesasRequest;
import br.com.cdb.controledespesas.core.domain.exception.BusinessRuleException;
import br.com.cdb.controledespesas.core.domain.model.Categoria;
import br.com.cdb.controledespesas.core.domain.model.Despesa;
import br.com.cdb.controledespesas.port.input.CategoriaInputPort;
import br.com.cdb.controledespesas.port.input.DespesaInputPort;
import br.com.cdb.controledespesas.port.output.CategoriaOutputPort;
import br.com.cdb.controledespesas.port.output.DespesaOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DespesaUseCase implements DespesaInputPort {

    @Autowired
    private CategoriaInputPort categoriaInputPort;

    @Autowired
    CategoriaOutputPort categoriaOutputPort;

    @Autowired
    DespesaOutputPort despesaOutputPort;


    @Autowired
    DespesaMapper despesaMapper;

    public Despesa salvarDespesa(DespesaRequest despesaRequest) {

        Categoria categoria = categoriaOutputPort.buscarPorId(despesaRequest.getCategoriaId())
                .orElseThrow(() -> new BusinessRuleException("Categoria não encontrada"));

        Despesa despesa = despesaMapper.toDomain(despesaRequest);
        despesa.setCategoriaId(categoria.getId());

        Despesa despesaSalva = despesaOutputPort.salvarDespesa(despesa);
        return despesaSalva;
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
}
