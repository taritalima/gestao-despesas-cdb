package br.com.cdb.controledespesas.adapter.input.controller;

import br.com.cdb.controledespesas.adapter.input.mapper.DespesaMapper;
import br.com.cdb.controledespesas.adapter.input.request.DespesaRequest;
import br.com.cdb.controledespesas.adapter.input.request.FiltroDespesasRequest;
import br.com.cdb.controledespesas.adapter.input.response.DespesaResponse;
import br.com.cdb.controledespesas.adapter.input.response.SomaDespesasResponse;
import br.com.cdb.controledespesas.core.domain.model.Despesa;
import br.com.cdb.controledespesas.infraestructure.DespesaUseCaseBean;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Despesas", description = "Endpoints para controle de despesas pessoais")
@RestController
@RequestMapping("/despesas")
public class DespesaController {

    private final DespesaUseCaseBean despesaUseCase;
    private final  DespesaMapper despesaMapper;

    public DespesaController(DespesaUseCaseBean despesaUseCase, DespesaMapper despesaMapper) {
        this.despesaUseCase = despesaUseCase;
        this.despesaMapper = despesaMapper;
    }

    @PostMapping("/{usuarioId}")
    public ResponseEntity<DespesaResponse> addDespesa(@PathVariable Long usuarioId, @Valid @RequestBody DespesaRequest despesaRequest){

        despesaRequest.setUsuarioId(usuarioId);

        Despesa despesa = despesaMapper.toDomain(despesaRequest);
        Despesa despesaSalva = despesaUseCase.salvarDespesa(despesa);
        DespesaResponse response = despesaMapper.toResponse(despesaSalva);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<SomaDespesasResponse> listarDespesasPorData(
            @Valid FiltroDespesasRequest filtroDespesasRequest) {

        SomaDespesasResponse response = despesaUseCase.listarDespesasComTotal(filtroDespesasRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/{usuarioId}")
    public ResponseEntity<DespesaResponse> atualizarDespesa(
            @PathVariable Long id,
            @PathVariable Long usuarioId,
            @Valid @RequestBody DespesaRequest despesaRequest) {

        Despesa despesaAtualizada = despesaUseCase.atualizarDespesa(id, usuarioId, despesaRequest);

        DespesaResponse response = despesaMapper.toResponse(despesaAtualizada);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}/{usuarioId}")
    public ResponseEntity<Void> deletarDespesaUsuario(@PathVariable Long id, @PathVariable Long usuarioId){
        despesaUseCase.deletarDespesaPorId(id, usuarioId);
        return ResponseEntity.noContent().build();
    }
}
