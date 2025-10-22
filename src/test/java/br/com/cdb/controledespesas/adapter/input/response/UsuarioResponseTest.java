package br.com.cdb.controledespesas.adapter.input.response;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UsuarioResponseTest {

    @Test
    void testUsuarioResponse() {
        UsuarioResponse usuario = new UsuarioResponse(1L, "Ibrahimovic");

        assertThat(usuario.getId()).isEqualTo(1L);
        assertThat(usuario.getNome()).isEqualTo("Ibrahimovic");

        usuario.setNome("Luana");
        assertThat(usuario.getNome()).isEqualTo("Luana");
    }
}
