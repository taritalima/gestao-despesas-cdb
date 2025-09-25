package br.com.cdb.controledespesas.adapter.input.controller;

import br.com.cdb.controledespesas.adapter.input.mapper.CategoriaMapper;
import br.com.cdb.controledespesas.adapter.input.request.CategoriaRequest;
import br.com.cdb.controledespesas.adapter.input.response.CategoriaResponse;
import br.com.cdb.controledespesas.core.domain.model.Categoria;
import br.com.cdb.controledespesas.infraestructure.CategoriaUseCaseBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoriaControllerTest {

    @Mock
    private CategoriaMapper categoriaMapper;

    @Mock
    private CategoriaUseCaseBean categoriaUseCase;

    @InjectMocks
    private CategoriaController categoriaController;

    private Categoria categoria;
    private CategoriaRequest categoriaRequest;
    private CategoriaResponse categoriaResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoria = new Categoria(1L, "Lanches");
        categoriaRequest = new CategoriaRequest();
        categoriaRequest.setNome("Lanches");
        categoriaResponse = new CategoriaResponse(1L, "Lanches");
    }

    @Test
    void deveAdicionarCategoria() {
        when(categoriaMapper.toDomain(categoriaRequest)).thenReturn(categoria);
        when(categoriaUseCase.salvarCategoria(categoria)).thenReturn(categoria);
        when(categoriaMapper.toResponse(categoria)).thenReturn(categoriaResponse);

        ResponseEntity<CategoriaResponse> response = categoriaController.addCategoria(categoriaRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(categoriaResponse, response.getBody());
        verify(categoriaUseCase, times(1)).salvarCategoria(categoria);
    }

    @Test
    void deveListarCategorias() {
        when(categoriaUseCase.buscarTodasCategorias()).thenReturn(List.of(categoria));
        when(categoriaMapper.toResponse(categoria)).thenReturn(categoriaResponse);

        ResponseEntity<List<CategoriaResponse>> response = categoriaController.listarTofasCategorias();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    
}
