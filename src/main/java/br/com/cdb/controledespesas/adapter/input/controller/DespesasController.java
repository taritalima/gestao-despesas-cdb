package br.com.cdb.controledespesas.adapter.input.controller;


import br.com.cdb.controledespesas.adapter.input.mapper.DespesaMapper;
import br.com.cdb.controledespesas.adapter.input.request.DespesaRequest;
import br.com.cdb.controledespesas.adapter.input.request.FiltroDespesasRequest;
import br.com.cdb.controledespesas.adapter.input.request.SomaDespesasRequest;
import br.com.cdb.controledespesas.adapter.input.response.DespesaResponse;
import br.com.cdb.controledespesas.adapter.output.entity.DespesaEntity;
import br.com.cdb.controledespesas.core.domain.model.Despesa;
import br.com.cdb.controledespesas.core.domain.usecase.DespesaUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@Tag(name = "Despesas", description = "Endpoints para controle de despesas pessoais")
@RestController
@RequestMapping("/despesas")
public class DespesasController {

    @Autowired
    private DespesaUseCase despesaUseCase;
    @Autowired
    private DespesaMapper despesaMapper;

    @PostMapping
    public ResponseEntity<DespesaResponse> addDespesa(@Valid @RequestBody DespesaRequest despesaRequest){
        Despesa despesaSalva = despesaUseCase.salvarDespesa(despesaRequest);

        DespesaResponse response = despesaMapper.toResponse(despesaSalva);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<SomaDespesasRequest> listarDespesasPorData(@Valid FiltroDespesasRequest filtroDespesasRequest) {
        List<Despesa> despesas = despesaUseCase.filtrarDespesas(filtroDespesasRequest);

        BigDecimal total = despesas.stream()
                .map(Despesa::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<DespesaResponse> despesasResponse = despesas.stream()
                .map(despesaMapper::toResponse)
                .toList();

        SomaDespesasRequest response = new SomaDespesasRequest(total, despesasResponse);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/{usuarioId}")
    public ResponseEntity<Despesa> atualizarDespesa(@PathVariable Long id,
                                                          @PathVariable Long usuarioId,
                                                          @Valid @RequestBody DespesaRequest despesaDTO) {
        Despesa update = despesaUseCase.atualizarDespesa(id, usuarioId, despesaDTO);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{id}/{usuarioId}")
    public ResponseEntity<Void> deletarDespesaUsuario(@PathVariable Long id, @PathVariable Long usuarioId){
        despesaUseCase.deletarDespesaPorId(id, usuarioId);
        return ResponseEntity.noContent().build();
    }
}
