package br.com.cdb.controledespesas.adapter.input.response;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class CategoriaResponseTest {

    @Test
    void testCategoriaResponse() {
        CategoriaResponse categoria = new CategoriaResponse(1L, "categoria1");

        assertThat(categoria.getId()).isEqualTo(1L);
        assertThat(categoria.getNome()).isEqualTo("categoria1");

        categoria.setId(2L);
        categoria.setNome("Doces");

        assertThat(categoria.getId()).isEqualTo(2L);
        assertThat(categoria.getNome()).isEqualTo("Doces");
    }
}
