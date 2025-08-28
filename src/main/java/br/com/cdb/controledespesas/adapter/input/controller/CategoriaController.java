package br.com.cdb.controledespesas.adapter.input.controller;


import br.com.cdb.controledespesas.adapter.input.mapper.CategoriaMapper;
import br.com.cdb.controledespesas.adapter.input.request.CategoriaRequest;
import br.com.cdb.controledespesas.adapter.input.response.CategoriaResponse;
import br.com.cdb.controledespesas.adapter.output.entity.CategoriaEntity;
import br.com.cdb.controledespesas.core.domain.model.Categoria;
import br.com.cdb.controledespesas.core.domain.usecase.CategoriaUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Categorias", description = "Endpoints para controle das Categorias")
@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    CategoriaUseCase categoriaService;

   @Autowired
    CategoriaMapper categoriaMapper;

    @PostMapping
    public ResponseEntity<CategoriaResponse> addCategoria(@Valid @RequestBody CategoriaRequest categoriaRequest){
        Categoria categoriaSalva = categoriaService.salvarCategoria(categoriaRequest);
        CategoriaResponse response = categoriaMapper.toResponse(categoriaSalva);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarCategoriaUsuario(@PathVariable Long id){
        categoriaService.deletarCategoria(id);
        return  ResponseEntity.noContent().build();
    }
}
