package br.com.cdb.controledespesas.adapter.input.documentation;


import br.com.cdb.controledespesas.adapter.input.request.UsuarioRequest;
import br.com.cdb.controledespesas.adapter.input.response.UsuarioResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface UsuarioDoc {

    @Operation(
            summary = "Cadastrar novo usuário",
            description = "Cria um novo usuário no sistema com os dados informados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
                    content = @Content(schema = @Schema(implementation = UsuarioResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    ResponseEntity<UsuarioResponse> addUsuario(@RequestBody UsuarioRequest usuarioRequest);


    @Operation(
            summary = "Deletar usuário",
            description = "Remove o usuário especificado do sistema. Só é possível remover usuários sem despesas associadas."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    ResponseEntity<?> deletarUsuario(@PathVariable Long id);


    @Operation(
            summary = "Atualizar dados do usuário",
            description = "Atualiza as informações do usuário com base nos dados fornecidos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = UsuarioResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    ResponseEntity<UsuarioResponse> alterarUsuario(@PathVariable Long id, @RequestBody UsuarioRequest usuarioRequest);
}
