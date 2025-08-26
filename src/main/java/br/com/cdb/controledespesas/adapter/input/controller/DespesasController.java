package br.com.cdb.controledespesas.adapter.input.controller;


import br.com.cdb.controledespesas.adapter.input.request.DespesaRequest;
import br.com.cdb.controledespesas.adapter.input.request.FiltroDespesasRequest;
import br.com.cdb.controledespesas.adapter.input.request.SomaDespesasRequest;
import br.com.cdb.controledespesas.adapter.output.entity.DespesaEntity;
import br.com.cdb.controledespesas.core.domain.usecase.DespesaUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Despesas", description = "Endpoints para controle de despesas pessoais")
@RestController
@RequestMapping("/despesas")
public class DespesasController {

    @Autowired
    private DespesaUseCase despesaUseCase;

    @PostMapping
    public ResponseEntity<DespesaEntity> addDespesa(@Valid @RequestBody DespesaRequest despesaDTO){
        DespesaEntity despesaSalva = despesaUseCase.salvarDespesa(despesaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(despesaSalva);
    }

    @GetMapping
    public ResponseEntity<SomaDespesasRequest> listarDespesasPorData(@Valid FiltroDespesasRequest filtroDespesasDTO) {
        SomaDespesasRequest soma = despesaUseCase.filtrarDespesas(filtroDespesasDTO);
        return ResponseEntity.ok(soma);
    }

    @PutMapping("/{id}/{usuarioId}")
    public ResponseEntity<DespesaEntity> atualizarDespesa(@PathVariable Long id,
                                                          @PathVariable Long usuarioId,
                                                          @Valid @RequestBody DespesaRequest despesaDTO) {
        DespesaEntity update = despesaUseCase.atualizarDespesa(id, usuarioId, despesaDTO);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{id}/{usuarioId}")
    public ResponseEntity<Void> deletarDespesaUsuario(@PathVariable Long id, @PathVariable Long usuarioId){
        despesaUseCase.deletarDespesaPorId(id, usuarioId);
        return ResponseEntity.noContent().build();
    }
}
