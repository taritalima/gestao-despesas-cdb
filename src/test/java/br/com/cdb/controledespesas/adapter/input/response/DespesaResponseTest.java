package br.com.cdb.controledespesas.adapter.input.response;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class DespesaResponseTest {

    @Test
    void testDespesaResponse() {
        DespesaResponse despesa = new DespesaResponse(
                1L, 10L, "sobremesa", BigDecimal.valueOf(110.50),
                LocalDate.of(2025, 10, 1), 5L
        );

        assertThat(despesa.getId()).isEqualTo(1L);
        assertThat(despesa.getCategoriaId()).isEqualTo(10L);
        assertThat(despesa.getDescricao()).isEqualTo("sobremesa");
        assertThat(despesa.getValor()).isEqualByComparingTo(BigDecimal.valueOf(110.50));
        assertThat(despesa.getPagoEm()).isEqualTo(LocalDate.of(2025, 10, 1));
        assertThat(despesa.getUsuarioId()).isEqualTo(5L);

        despesa.setDescricao("Petit Gateau");
        despesa.setValor(BigDecimal.valueOf(80.00));
        assertThat(despesa.getDescricao()).isEqualTo("Petit Gateau");
        assertThat(despesa.getValor()).isEqualByComparingTo(BigDecimal.valueOf(80.00));
    }
}
