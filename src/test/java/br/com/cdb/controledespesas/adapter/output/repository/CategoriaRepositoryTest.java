package br.com.cdb.controledespesas.adapter.output.repository;

import br.com.cdb.controledespesas.adapter.input.mapper.CategoriaMapper;
import br.com.cdb.controledespesas.adapter.output.entity.CategoriaEntity;
import br.com.cdb.controledespesas.core.domain.model.Categoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CategoriaRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private CategoriaMapper categoriaMapper;

    @InjectMocks
    private CategoriaRepository categoriaRepository;

    private Categoria categoria;
    private CategoriaEntity entity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoria = new Categoria(1L, "Categoria1");
        entity = new CategoriaEntity(1L, "Categoria1");
    }

    @Test
    void testSalvarCategoria() {
        when(categoriaMapper.toEntity(categoria)).thenReturn(entity);
        when(categoriaMapper.toDomain(any(CategoriaEntity.class))).thenReturn(categoria);

        when(jdbcTemplate.execute(any(ConnectionCallback.class)))
                .then(invocation -> categoriaMapper.toDomain(entity));

        Categoria result = categoriaRepository.salvarCategoria(categoria);

        assertNotNull(result);
        assertEquals("Categoria1", result.getNome());
        verify(categoriaMapper).toEntity(any());
        verify(categoriaMapper).toDomain(any(CategoriaEntity.class));
    }

    @Test
    void testBuscarPorId() {
        when(jdbcTemplate.query(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenReturn(List.of(new Categoria(1L, "Categoria2")));

        Optional<Categoria> result = categoriaRepository.buscarPorId(1L);

        assertTrue(result.isPresent());
        assertEquals("Categoria2", result.get().getNome());
    }

    @Test
    void testBuscarPorName() {
        Categoria categoriaEsperada = new Categoria(1L, "Lanches");

        when(jdbcTemplate.query(
                eq("SELECT * FROM public.fn_buscar_categoria_por_nome(?)"),
                any(RowMapper.class),
                eq("Lanches")
        )).thenReturn(List.of(categoriaEsperada));

        Optional<Categoria> resultado = categoriaRepository.buscarPorName("Lanches");

        assertTrue(resultado.isPresent());
        assertEquals("Lanches", resultado.get().getNome());
    }


    @Test
    void testBuscarTodasCategoria() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenReturn(List.of(
                        new Categoria(1L, "Categoria2"),
                        new Categoria(2L, "Bebidas")
                ));

        List<Categoria> result = categoriaRepository.buscarTodasCategoria();

        assertEquals(2, result.size());
        assertEquals("Bebidas", result.get(1).getNome());
    }




}
