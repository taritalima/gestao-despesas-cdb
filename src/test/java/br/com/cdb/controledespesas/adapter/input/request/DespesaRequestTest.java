package br.com.cdb.controledespesas.adapter.input.request;


import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DespesaRequestTest {

    @Test
    void testGettersAndSetters() {
        DespesaRequest request = new DespesaRequest();

        request.setUsuarioId(1L);
        request.setCategoriaId(5L);
        request.setDescricao("Almoço");
        request.setValor(BigDecimal.valueOf(50.0));
        request.setPagoEm(LocalDate.now());
        request.setCriadoEm(LocalDateTime.now());

        assertEquals(1L, request.getUsuarioId());
        assertEquals(5L, request.getCategoriaId());
        assertEquals("Almoço", request.getDescricao());
        assertEquals(BigDecimal.valueOf(50.0), request.getValor());
        assertNotNull(request.getPagoEm());
        assertNotNull(request.getCriadoEm());
    }
}
