package br.com.cdb.controledespesas.adapter.input.response;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SomaDespesasResponseTest {

    @Test
    void testSomaDespesasResponse() {
        DespesaResponse despesa1 = new DespesaResponse(1L, 10L, "Despesa 1", BigDecimal.valueOf(100), null, 1L);
        DespesaResponse despesa2 = new DespesaResponse(2L, 11L, "Despesa 2", BigDecimal.valueOf(50), null, 1L);

        SomaDespesasResponse soma = new SomaDespesasResponse(
                BigDecimal.valueOf(150),
                List.of(despesa1, despesa2)
        );

        assertThat(soma.getTotalGasto()).isEqualByComparingTo(BigDecimal.valueOf(150));
        assertThat(soma.getDespesas()).hasSize(2).contains(despesa1, despesa2);

        soma.setTotalGasto(BigDecimal.valueOf(200));
        soma.setDespesas(List.of(despesa1));
        assertThat(soma.getTotalGasto()).isEqualByComparingTo(BigDecimal.valueOf(200));
        assertThat(soma.getDespesas()).hasSize(1);
    }
}
