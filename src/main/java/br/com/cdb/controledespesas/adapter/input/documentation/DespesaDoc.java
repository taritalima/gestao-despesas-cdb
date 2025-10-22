package br.com.cdb.controledespesas.adapter.input.documentation;


import br.com.cdb.controledespesas.adapter.input.request.DespesaRequest;
import br.com.cdb.controledespesas.adapter.input.request.FiltroDespesasRequest;
import br.com.cdb.controledespesas.adapter.input.response.DespesaResponse;
import br.com.cdb.controledespesas.adapter.input.response.SomaDespesasResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface DespesaDoc {

    @Operation(
            summary = "Cadastrar nova despesa",
            description = "Cria uma despesa para o usuário especificado. " +
                    "A categoria deve existir e a data de pagamento não pode ser futura."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Despesa criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou categoria não encontrada")
    })
    ResponseEntity<DespesaResponse> addDespesa(@PathVariable Long usuarioId, @RequestBody DespesaRequest despesaRequest);


    @Operation(summary = "Listar despesas filtradas",
            description = "Retorna as despesas do usuário filtradas por categoria e período.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de despesas com soma total",
                    content = @Content(schema = @Schema(implementation = SomaDespesasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Filtros inválidos")
    })
    ResponseEntity<SomaDespesasResponse> listarDespesasPorData(FiltroDespesasRequest filtroDespesasRequest);


    @Operation(summary = "Atualizar despesa existente",
            description = "Atualiza os dados da despesa. O usuário só pode atualizar suas próprias despesas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Despesa atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = DespesaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Despesa não encontrada")
    })
    ResponseEntity<DespesaResponse> atualizarDespesa(@PathVariable Long id,
                                                     @PathVariable Long usuarioId,
                                                     @RequestBody DespesaRequest despesaRequest);


    @Operation(summary = "Deletar despesa",
            description = "Remove a despesa especificada. O usuário só pode remover suas próprias despesas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Despesa removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Despesa não encontrada")
    })
    ResponseEntity<Void> deletarDespesaUsuario(@PathVariable Long id, @PathVariable Long usuarioId);
}
