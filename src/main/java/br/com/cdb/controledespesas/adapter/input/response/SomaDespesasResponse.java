package br.com.cdb.controledespesas.adapter.input.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SomaDespesasResponse {
    private BigDecimal totalGasto;
    private List<DespesaResponse> despesas;

}
