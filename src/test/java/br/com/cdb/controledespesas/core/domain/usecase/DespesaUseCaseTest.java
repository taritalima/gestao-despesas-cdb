package br.com.cdb.controledespesas.core.domain.usecase;

import br.com.cdb.controledespesas.adapter.input.mapper.DespesaMapper;
import br.com.cdb.controledespesas.adapter.input.request.DespesaRequest;
import br.com.cdb.controledespesas.adapter.input.request.FiltroDespesasRequest;
import br.com.cdb.controledespesas.adapter.input.response.DespesaResponse;
import br.com.cdb.controledespesas.adapter.input.response.SomaDespesasResponse;
import br.com.cdb.controledespesas.core.domain.exception.BusinessRuleException;
import br.com.cdb.controledespesas.core.domain.model.Categoria;
import br.com.cdb.controledespesas.core.domain.model.Despesa;
import br.com.cdb.controledespesas.port.output.CategoriaOutputPort;
import br.com.cdb.controledespesas.port.output.DespesaOutputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DespesaUseCaseTest {

    @Mock
    private CategoriaOutputPort categoriaOutputPort;

    @Mock
    private DespesaOutputPort despesaOutputPort;

    @Mock
    private DespesaMapper despesaMapper;

    @InjectMocks
    private DespesaUseCase despesaUseCase;

    private Despesa despesa;
    private Categoria categoria;
    private DespesaRequest despesaRequest;
    private FiltroDespesasRequest filtro;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        categoria = new Categoria(1L, "Alimentação");
        despesa = new Despesa(
                1L,
                1L,
                1L,
                "Almoço",
                BigDecimal.valueOf(50),
                LocalDate.now(),
                LocalDateTime.now()
        );


        despesaRequest = new DespesaRequest(
                1L,
                "Jantar",
                BigDecimal.valueOf(70),
                LocalDate.now(),
                null,
                1L
        );



        filtro = new FiltroDespesasRequest();
        filtro.setUsuarioId(1L);
    }

    @Test
    void deveSalvarDespesaComCategoriaExistente() {
        when(categoriaOutputPort.buscarPorId(1L)).thenReturn(Optional.of(categoria));
        when(despesaOutputPort.salvarDespesa(despesa)).thenReturn(despesa);

        Despesa resultado = despesaUseCase.salvarDespesa(despesa);

        assertNotNull(resultado);
        assertEquals("Almoço", resultado.getDescricao());
        verify(despesaOutputPort, times(1)).salvarDespesa(despesa);
    }

    @Test
    void deveLancarExcecaoQuandoCategoriaNaoExistirAoSalvar() {
        when(categoriaOutputPort.buscarPorId(1L)).thenReturn(Optional.empty());

        assertThrows(BusinessRuleException.class, () -> despesaUseCase.salvarDespesa(despesa));
    }

    @Test
    void deveFiltrarDespesas() {
        when(despesaOutputPort.filtrarDespesas(1L, null, null, null)).thenReturn(List.of(despesa));

        List<Despesa> resultado = despesaUseCase.filtrarDespesas(filtro);

        assertEquals(1, resultado.size());
        verify(despesaOutputPort, times(1)).filtrarDespesas(1L, null, null, null);
    }

    
}
