package br.com.cdb.controledespesas.adapter.input.response;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ErroResponseTest {

    @Test
    void testErroResponse() {
        LocalDateTime now = LocalDateTime.now();
        ErroResponse erro = new ErroResponse(
                now, 404, "/despesa/1", Map.of("mensagem", "Despesa não encontrada")
        );

        assertThat(erro.getTimestamp()).isEqualTo(now);
        assertThat(erro.getStatus()).isEqualTo(404);
        assertThat(erro.getPath()).isEqualTo("/despesa/1");
        assertThat(erro.getErros()).containsEntry("mensagem", "Despesa não encontrada");

        erro.setStatus(500);
        erro.setPath("/despesa");
        assertThat(erro.getStatus()).isEqualTo(500);
        assertThat(erro.getPath()).isEqualTo("/despesa");
    }
}
