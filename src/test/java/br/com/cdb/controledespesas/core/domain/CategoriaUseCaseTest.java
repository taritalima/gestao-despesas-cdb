package br.com.cdb.controledespesas.core.domain;

import br.com.cdb.controledespesas.core.domain.exception.BusinessRuleException;
import br.com.cdb.controledespesas.core.domain.model.Categoria;
import br.com.cdb.controledespesas.port.output.CategoriaOutputPort;
import br.com.cdb.controledespesas.port.output.DespesaOutputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoriaUseCaseTest {

    @Mock
    private CategoriaOutputPort categoriaOutputPort;

    @Mock
    private DespesaOutputPort despesaOutputPort;

    @InjectMocks
    private CategoriaUseCase categoriaUseCase;

    private Categoria categoriaTeste;

    @BeforeEach
    void setUp(){
        categoriaTeste = new Categoria(1L, "Bebidas");
    }

    @Test
    @DisplayName("Deve salvar uma categoria com sucesso quando o nome não existe")
    void SalvarCategoriaNovo() {
        // GIVEN
        when(categoriaOutputPort.buscarPorName(categoriaTeste.getNome())).thenReturn(Optional.empty());
        when(categoriaOutputPort.salvarCategoria(any(Categoria.class))).thenReturn(categoriaTeste);

        // WHEN
        Categoria categoriaSalva = categoriaUseCase.salvarCategoria(categoriaTeste);

        // THEN
        assertNotNull(categoriaSalva);
        assertEquals(categoriaTeste.getNome(), categoriaSalva.getNome());
        verify(categoriaOutputPort).buscarPorName(categoriaTeste.getNome());
        verify(categoriaOutputPort).salvarCategoria(categoriaTeste);
    }

    @Test
    @DisplayName("Não deve salvar categoria com nome duplicado")
    void naoDeveSalvarCategoriaComNomeDuplicado() {
        // GIVEN
        when(categoriaOutputPort.buscarPorName(categoriaTeste.getNome())).thenReturn(Optional.of(categoriaTeste));

        // WHEN + THEN
        BusinessRuleException exception = assertThrows(BusinessRuleException.class,
                () -> categoriaUseCase.salvarCategoria(categoriaTeste));

        assertEquals("Já existe Categoria com esse nome!", exception.getMessage());
        verify(categoriaOutputPort, never()).salvarCategoria(any(Categoria.class));
    }
}
