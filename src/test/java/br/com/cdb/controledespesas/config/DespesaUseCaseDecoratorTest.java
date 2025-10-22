package br.com.cdb.controledespesas.config;

import br.com.cdb.controledespesas.adapter.input.request.DespesaRequest;
import br.com.cdb.controledespesas.adapter.input.request.FiltroDespesasRequest;
import br.com.cdb.controledespesas.adapter.input.response.DespesaResponse;
import br.com.cdb.controledespesas.adapter.input.response.SomaDespesasResponse;
import br.com.cdb.controledespesas.core.domain.decorator.DespesaUseCaseLoggerDecorator;
import br.com.cdb.controledespesas.core.domain.model.Despesa;
import br.com.cdb.controledespesas.core.domain.usecase.DespesaUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;


public class DespesaUseCaseDecoratorTest {

    private DespesaUseCase delegate;
    private DespesaUseCaseLoggerDecorator decorator;

    private Despesa despesa;
    private DespesaRequest despesaRequest;
    private FiltroDespesasRequest filtro;

    @BeforeEach
    void setUp() {
        delegate = mock(DespesaUseCase.class);
        decorator = new DespesaUseCaseLoggerDecorator(delegate);

        despesa = new Despesa(1L, 1L, 1L, "Almoço", BigDecimal.valueOf(50), LocalDate.now(), null);

        despesaRequest = new DespesaRequest(1L, "Jantar", BigDecimal.valueOf(70), LocalDate.now(), null, 1L);
        filtro = new FiltroDespesasRequest();
        filtro.setUsuarioId(1L);
    }

    @Test
    void salvarDespesa_deveDelegar() {
        when(delegate.salvarDespesa(despesa)).thenReturn(despesa);

        Despesa resultado = decorator.salvarDespesa(despesa);

        assertEquals(despesa, resultado);
        verify(delegate).salvarDespesa(despesa);
    }
    @Test
    void deletarDespesa_deveDelegar() {
        doNothing().when(delegate).deletarDespesaPorId(1L, 1L);

        decorator.deletarDespesaPorId(1L, 1L);

        verify(delegate).deletarDespesaPorId(1L, 1L);
    }

    @Test
    void atualizarDespesa_deveDelegar() {
        when(delegate.atualizarDespesa(1L, 1L, despesaRequest)).thenReturn(despesa);

        Despesa resultado = decorator.atualizarDespesa(1L, 1L, despesaRequest);

        assertEquals(despesa, resultado);
        verify(delegate).atualizarDespesa(1L, 1L, despesaRequest);
    }

    @Test
    void filtrarDespesas_deveDelegar() {
        when(delegate.filtrarDespesas(filtro)).thenReturn(List.of(despesa));

        List<Despesa> resultado = decorator.filtrarDespesas(filtro);

        assertEquals(1, resultado.size());
        verify(delegate).filtrarDespesas(filtro);
    }

    @Test
    void listarDespesasComTotal_deveDelegar() {
        SomaDespesasResponse response = new SomaDespesasResponse();
        response.setTotalGasto(BigDecimal.valueOf(150));

        when(delegate.listarDespesasComTotal(filtro)).thenReturn(response);

        SomaDespesasResponse resultado = decorator.listarDespesasComTotal(filtro);

        assertThat(resultado).isEqualTo(response);
        verify(delegate, times(1)).listarDespesasComTotal(filtro);
    }
}
