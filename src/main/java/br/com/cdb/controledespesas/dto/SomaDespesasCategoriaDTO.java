package br.com.cdb.controledespesas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SomaDespesasCategoriaDTO {

    private BigDecimal totalGasto;
    private List<DespesaDTO> despesas;

}
