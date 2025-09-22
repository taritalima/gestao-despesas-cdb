package br.com.cdb.controledespesas.adapter.input.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DespesaResponse {
    private Long id;
    private Long categoriaId;
    private String descricao;
    private BigDecimal valor;
    private LocalDate pagoEm;
    private Long usuarioId;
}
