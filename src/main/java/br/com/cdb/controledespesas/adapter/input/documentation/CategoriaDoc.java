package br.com.cdb.controledespesas.adapter.input.documentation;

import br.com.cdb.controledespesas.adapter.input.request.CategoriaRequest;
import br.com.cdb.controledespesas.adapter.input.response.CategoriaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CategoriaDoc {

    @Operation(
            summary = "Cadastrar nova categoria",
            description = "Cria uma nova categoria. O nome da categoria deve ser único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso",
                    content = @Content(schema = @Schema(implementation = CategoriaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou nome duplicado")
    })
    ResponseEntity<CategoriaResponse> addCategoria(@RequestBody CategoriaRequest categoriaRequest);


    @Operation(
            summary = "Deletar categoria",
            description = "Remove a categoria especificada. Só é possível remover categorias sem despesas associadas."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria removida com sucesso"),
            @ApiResponse(responseCode = "400", description = "Não é possível remover categoria com despesas associadas"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    ResponseEntity<?> deletarCategoriaUsuario(@PathVariable Long id);


    @Operation(
            summary = "Buscar categoria por ID",
            description = "Retorna os dados da categoria especificada pelo ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada",
                    content = @Content(schema = @Schema(implementation = CategoriaResponse.class))),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    ResponseEntity<CategoriaResponse> buscarCategoriaId(@PathVariable Long id);


    @Operation(
            summary = "Listar todas as categorias",
            description = "Retorna uma lista com todas as categorias cadastradas."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de categorias",
                    content = @Content(schema = @Schema(implementation = CategoriaResponse.class)))
    })
    ResponseEntity<List<CategoriaResponse>> listarTofasCategorias();
}
