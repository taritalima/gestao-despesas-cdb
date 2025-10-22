package br.com.cdb.controledespesas.config;


import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FiltroDespesasConfigTest {

    @Test
    void deveCobrirSetters() {
        FiltroDespesasConfig config = FiltroDespesasConfig.getInstance();

        LocalDate inicio = LocalDate.of(2025, 10, 1);
        LocalDate fim = LocalDate.of(2025, 10, 31);

        config.setDataInicioPadrao(inicio);
        config.setDataFimPadrao(fim);

        assertEquals(inicio, config.getDataInicioPadrao());
        assertEquals(fim, config.getDataFimPadrao());
    }
}
