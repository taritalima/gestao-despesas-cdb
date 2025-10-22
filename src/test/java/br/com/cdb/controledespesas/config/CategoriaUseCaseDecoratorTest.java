package br.com.cdb.controledespesas.config;

import br.com.cdb.controledespesas.core.domain.decorator.CategoriaUseCaseLoggerDecorator;
import br.com.cdb.controledespesas.core.domain.model.Categoria;
import br.com.cdb.controledespesas.port.input.CategoriaInputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CategoriaUseCaseDecoratorTest {

    private CategoriaInputPort delegate;
    private CategoriaUseCaseLoggerDecorator decorator;

    private Categoria categoriaTeste;

    @BeforeEach
    void setUp() {
        delegate = mock(CategoriaInputPort.class);
        decorator = new CategoriaUseCaseLoggerDecorator(delegate);

        categoriaTeste = new Categoria(1L, "Bebidas");
    }

    @Test
    void salvarCategoria_deveDelegar() {
        when(delegate.salvarCategoria(categoriaTeste)).thenReturn(categoriaTeste);

        Categoria resultado = decorator.salvarCategoria(categoriaTeste);

        assertEquals(categoriaTeste, resultado);
        verify(delegate, times(1)).salvarCategoria(categoriaTeste);
    }

    @Test
    void deletarCategoria_deveDelegar() {
        doNothing().when(delegate).deletarCategoria(categoriaTeste.getId());

        decorator.deletarCategoria(categoriaTeste.getId());

        verify(delegate, times(1)).deletarCategoria(categoriaTeste.getId());
    }

    @Test
    void listarCategorias_deveDelegar() {
        List<Categoria> categorias = Arrays.asList(
                new Categoria(1L, "Bebidas"),
                new Categoria(2L, "Alimentação")
        );

        when(delegate.buscarTodasCategorias()).thenReturn(categorias);

        List<Categoria> resultado = decorator.buscarTodasCategorias();

        assertEquals(2, resultado.size());
        assertEquals("Bebidas", resultado.get(0).getNome());
        assertEquals("Alimentação", resultado.get(1).getNome());
        verify(delegate, times(1)).buscarTodasCategorias();
    }

    @Test
    void buscarCategoriaId_deveDelegar() {
        when(delegate.buscarCategoriaId(1L)).thenReturn(categoriaTeste);

        Categoria resultado = decorator.buscarCategoriaId(1L);

        assertEquals(categoriaTeste, resultado);
        verify(delegate, times(1)).buscarCategoriaId(1L);
    }
}
