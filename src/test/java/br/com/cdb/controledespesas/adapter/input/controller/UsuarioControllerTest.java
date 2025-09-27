package br.com.cdb.controledespesas.adapter.input.controller;

import br.com.cdb.controledespesas.adapter.input.mapper.UsuarioMapper;
import br.com.cdb.controledespesas.adapter.input.request.UsuarioRequest;
import br.com.cdb.controledespesas.adapter.input.response.UsuarioResponse;
import br.com.cdb.controledespesas.core.domain.model.Usuario;
import br.com.cdb.controledespesas.core.domain.usecase.UsuarioUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {

    @Mock
    private UsuarioMapper usuarioMapper;

    @Mock
    private UsuarioUseCase usuarioUseCase;

    @InjectMocks
    private UsuarioController usuarioController;

    private Usuario usuario;
    private UsuarioRequest usuarioRequest;
    private UsuarioResponse usuarioResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuario = new Usuario(1L, "Rafaela Farias");
        usuarioRequest = new UsuarioRequest();
        usuarioRequest.setNome("Rafaela Farias");


        usuarioResponse = new UsuarioResponse(1L, "Rafaela Farias");
    }

    @Test
    void deveAdicionarUsuario() {
        when(usuarioMapper.toDomain(usuarioRequest)).thenReturn(usuario);
        when(usuarioUseCase.salvarUsuario(usuario)).thenReturn(usuario);
        when(usuarioMapper.toResponse(usuario)).thenReturn(usuarioResponse);

        ResponseEntity<UsuarioResponse> response = usuarioController.addUsuario(usuarioRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(usuarioResponse, response.getBody());
        verify(usuarioUseCase, times(1)).salvarUsuario(usuario);
    }

    @Test
    void deveAlterarUsuario() {
        usuarioRequest.setId(1L);

        when(usuarioMapper.toDomain(usuarioRequest)).thenReturn(usuario);
        when(usuarioUseCase.alterarInfoUsuario(usuario)).thenReturn(usuario);
        when(usuarioMapper.toResponse(usuario)).thenReturn(usuarioResponse);

        ResponseEntity<UsuarioResponse> response = usuarioController.alterarUsuario(1L, usuarioRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuarioResponse, response.getBody());
        verify(usuarioUseCase, times(1)).alterarInfoUsuario(usuario);
    }

    
}
