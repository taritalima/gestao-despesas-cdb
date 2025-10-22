package br.com.cdb.controledespesas.adapter.input.request;

import br.com.cdb.controledespesas.adapter.input.response.DespesaResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SomaDespesaRequestTest {

    @Test
    void testGettersAndSetters() {
        DespesaResponse despesa = new DespesaResponse();
        List<DespesaResponse> lista = List.of(despesa);

        SomaDespesasRequest request = new SomaDespesasRequest();
        request.setTotalGasto(BigDecimal.valueOf(100));
        request.setDespesas(lista);

        assertEquals(BigDecimal.valueOf(100), request.getTotalGasto());
        assertEquals(lista, request.getDespesas());

        SomaDespesasRequest request2 = new SomaDespesasRequest(BigDecimal.valueOf(200), lista);
        assertEquals(BigDecimal.valueOf(200), request2.getTotalGasto());
        assertEquals(lista, request2.getDespesas());
    }
}
