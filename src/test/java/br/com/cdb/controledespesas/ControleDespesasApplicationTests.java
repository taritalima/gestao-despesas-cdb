package br.com.cdb.controledespesas;

import org.junit.jupiter.api.Test;


class ControleDespesasApplicationMainTest {

    @Test
    void deveExecutarMainSemErro() {
        System.setProperty("spring.main.web-application-type", "none");

        ControleDespesasApplication.main(new String[]{});

        System.clearProperty("spring.main.web-application-type");
    }
}

