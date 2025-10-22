package br.com.cdb.controledespesas.adapter.input.request;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

 class FiltroDespesaRequestTest {

    @Test
    void testGettersSetters() {
        FiltroDespesasRequest filtro = new FiltroDespesasRequest();
        filtro.setUsuarioId(1L);
        filtro.setCategoriaId(5L);
        filtro.setDe(LocalDate.now());
        filtro.setAte(LocalDate.now());

        assertEquals(1L, filtro.getUsuarioId());
        assertEquals(5L, filtro.getCategoriaId());
        assertNotNull(filtro.getDe());
        assertNotNull(filtro.getAte());
    }
}
