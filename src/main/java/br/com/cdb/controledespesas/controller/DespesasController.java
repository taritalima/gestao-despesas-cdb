package br.com.cdb.controledespesas.controller;


import br.com.cdb.controledespesas.dto.DespesaDTO;
import br.com.cdb.controledespesas.dto.FiltroDespesasDTO;
import br.com.cdb.controledespesas.dto.SomaDespesasCategoriaDTO;
import br.com.cdb.controledespesas.entity.Despesa;
import br.com.cdb.controledespesas.repository.CategoriaRepository;
import br.com.cdb.controledespesas.service.DespesaService;
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
    private DespesaService despesaService;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @PostMapping
    public ResponseEntity<Despesa> addDespesa(@Valid @RequestBody DespesaDTO despesaDTO){
        Despesa despesaSalva = despesaService.salvarDespesa(despesaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(despesaSalva);
    }

    @GetMapping
    public ResponseEntity<SomaDespesasCategoriaDTO> listarDespesasPorData(@Valid FiltroDespesasDTO filtroDespesasDTO) {
        SomaDespesasCategoriaDTO soma = despesaService.filtrarDespesas(filtroDespesasDTO);
        return ResponseEntity.ok(soma);

    }

    @PutMapping("/{id}/{usuarioId}")
    public ResponseEntity<Despesa> atualizarDespesa(@PathVariable Long id,@PathVariable Long usuarioId, @Valid @RequestBody DespesaDTO despesaDTO) {
        Despesa update = despesaService.atualizarDespesa(id, usuarioId, despesaDTO);
        return ResponseEntity.ok(update);

    }

    @DeleteMapping("/{id}/{usuarioId}")
    public ResponseEntity<?> deletarDespesaUsuario(@PathVariable Long id, @PathVariable Long usuarioId){
        despesaService.deletarDespesaPorId(id, usuarioId);
        return  ResponseEntity.noContent().build();
    }
}
