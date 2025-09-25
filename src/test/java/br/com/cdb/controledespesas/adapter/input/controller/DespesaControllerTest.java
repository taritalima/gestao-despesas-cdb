package br.com.cdb.controledespesas.adapter.input.controller;

import br.com.cdb.controledespesas.adapter.input.mapper.DespesaMapper;
import br.com.cdb.controledespesas.adapter.input.request.DespesaRequest;
import br.com.cdb.controledespesas.adapter.input.request.FiltroDespesasRequest;
import br.com.cdb.controledespesas.adapter.input.response.DespesaResponse;
import br.com.cdb.controledespesas.adapter.input.response.SomaDespesasResponse;
import br.com.cdb.controledespesas.core.domain.model.Despesa;
import br.com.cdb.controledespesas.infraestructure.DespesaUseCaseBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DespesaControllerTest {

    @Mock
    private DespesaUseCaseBean despesaUseCase;

    @Mock
    private DespesaMapper despesaMapper;

    @InjectMocks
    private DespesaController despesaController;

    private Despesa despesa;
    private DespesaRequest despesaRequest;
    private DespesaResponse despesaResponse;
    private SomaDespesasResponse somaDespesasResponse;

    @BeforeEach
    void setUp() {
        despesaResponse = new DespesaResponse(
                1L,
                10L,
                "Almoço",
                BigDecimal.valueOf(100.0),
                LocalDate.now(),
                5L
        );

        somaDespesasResponse = new SomaDespesasResponse(
                BigDecimal.valueOf(100.0),
                List.of(despesaResponse)
        );
    }


    @Test
    void deveAdicionarDespesa() {
        when(despesaMapper.toDomain(despesaRequest)).thenReturn(despesa);
        when(despesaUseCase.salvarDespesa(despesa)).thenReturn(despesa);
        when(despesaMapper.toResponse(despesa)).thenReturn(despesaResponse);

        ResponseEntity<DespesaResponse> response = despesaController.addDespesa(1L, despesaRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(despesaResponse, response.getBody());
        verify(despesaUseCase, times(1)).salvarDespesa(despesa);
    }

    @Test
    void deveListarDespesas() {
        FiltroDespesasRequest filtro = new FiltroDespesasRequest();
        when(despesaUseCase.listarDespesasComTotal(filtro)).thenReturn(somaDespesasResponse);

        ResponseEntity<SomaDespesasResponse> response = despesaController.listarDespesasPorData(filtro);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(somaDespesasResponse, response.getBody());
    }

    @Test
    void deveAtualizarDespesa() {
        when(despesaUseCase.atualizarDespesa(eq(1L), eq(1L), any(DespesaRequest.class))).thenReturn(despesa);
        when(despesaMapper.toResponse(despesa)).thenReturn(despesaResponse);

        ResponseEntity<DespesaResponse> response = despesaController.atualizarDespesa(1L, 1L, despesaRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(despesaResponse, response.getBody());
    }

    @Test
    void deveDeletarDespesa() {
        ResponseEntity<Void> response = despesaController.deletarDespesaUsuario(1L, 1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(despesaUseCase, times(1)).deletarDespesaPorId(1L, 1L);
    }
}
