package br.com.cdb.controledespesas.adapter.input.controller;


import br.com.cdb.controledespesas.adapter.input.mapper.CategoriaMapper;
import br.com.cdb.controledespesas.adapter.input.request.CategoriaRequest;
import br.com.cdb.controledespesas.adapter.input.response.CategoriaResponse;
import br.com.cdb.controledespesas.core.domain.model.Categoria;
import br.com.cdb.controledespesas.infraestructure.CategoriaUseCaseBean;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Categorias", description = "Endpoints para controle das Categorias")
@RestController
@RequestMapping("/categorias")
public class CategoriaController {


   private final CategoriaMapper  categoriaMapper;
   private final CategoriaUseCaseBean categoriaUseCase;

    public CategoriaController( CategoriaMapper categoriaMapper, CategoriaUseCaseBean categoriaUseCase) {
        this.categoriaMapper = categoriaMapper;
        this.categoriaUseCase = categoriaUseCase;
    }

    @PostMapping
    public ResponseEntity<CategoriaResponse> addCategoria(@Valid @RequestBody CategoriaRequest categoriaRequest) {
        Categoria categoria = categoriaMapper.toDomain(categoriaRequest);
        Categoria categoriaSalva = categoriaUseCase.salvarCategoria(categoria);
        CategoriaResponse response = categoriaMapper.toResponse(categoriaSalva);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarCategoriaUsuario(@PathVariable Long id){
        categoriaUseCase.deletarCategoria(id);
        return  ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> buscarCategoriaId(@PathVariable Long id) {
        Categoria categoria = categoriaUseCase.buscarCategoriaId(id);
        CategoriaResponse response = categoriaMapper.toResponse(categoria);
        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public ResponseEntity<List<CategoriaResponse>> listarTofasCategorias() {
        List<Categoria> categorias = categoriaUseCase.buscarTodasCategorias();
        List<CategoriaResponse> response = categorias.stream()
                .map(categoriaMapper::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }
}
