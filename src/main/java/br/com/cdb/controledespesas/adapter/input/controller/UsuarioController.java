package br.com.cdb.controledespesas.adapter.input.controller;


import br.com.cdb.controledespesas.adapter.input.documentation.UsuarioDoc;
import br.com.cdb.controledespesas.adapter.input.mapper.UsuarioMapper;
import br.com.cdb.controledespesas.adapter.input.request.UsuarioRequest;
import br.com.cdb.controledespesas.adapter.input.response.UsuarioResponse;
import br.com.cdb.controledespesas.core.domain.model.Usuario;
import br.com.cdb.controledespesas.port.input.UsuarioInputPort;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Usuarios")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController implements UsuarioDoc {

    private final UsuarioMapper usuarioMapper;
    private final UsuarioInputPort usuarioUseCase;

    public UsuarioController(UsuarioMapper usuarioMapper, UsuarioInputPort usuarioUseCase) {
        this.usuarioMapper = usuarioMapper;
        this.usuarioUseCase = usuarioUseCase;
    }


    @PostMapping
    public ResponseEntity<UsuarioResponse> addUsuario(@Valid @RequestBody UsuarioRequest usuarioRequest) {
        Usuario usuario = usuarioMapper.toDomain(usuarioRequest);
        Usuario usuarioSalvo = usuarioUseCase.salvarUsuario(usuario);
        UsuarioResponse response = usuarioMapper.toResponse(usuarioSalvo);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable Long id){
        usuarioUseCase.deletarUsuario(id);
        return  ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> alterarUsuario(@PathVariable Long id,@Valid @RequestBody UsuarioRequest usuarioRequest) {

        usuarioRequest.setId(id);
        Usuario usuario = usuarioMapper.toDomain(usuarioRequest);
        Usuario usuarioAtualizado = usuarioUseCase.alterarInfoUsuario(usuario);
        UsuarioResponse response = usuarioMapper.toResponse(usuarioAtualizado);
        return ResponseEntity.ok(response);
    }
}
