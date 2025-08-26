package br.com.cdb.controledespesas.adapter.input.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SomaDespesasRequest {

    private BigDecimal totalGasto;
    private List<DespesaRequest> despesas;

}
