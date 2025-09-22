package br.com.cdb.controledespesas.adapter.output.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DespesaEntity {

    private Long id;
    private Long categoriaId;
    private String descricao;
    private BigDecimal  valor;
    private LocalDate pagoEm;
    private LocalDateTime criadoEm;
    private Long usuarioId;

}
