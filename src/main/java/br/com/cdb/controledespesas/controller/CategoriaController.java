package br.com.cdb.controledespesas.controller;


import br.com.cdb.controledespesas.dto.CategoriaDTO;
import br.com.cdb.controledespesas.entity.Categoria;
import br.com.cdb.controledespesas.service.CategoriaService;
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
    CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<Categoria> addCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO){
        Categoria categoriaSalva = categoriaService.salvarCategoria(categoriaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarDespesaUsuario(@PathVariable Long id){
        categoriaService.deletarCategoria(id);
        return  ResponseEntity.noContent().build();
    }
}
