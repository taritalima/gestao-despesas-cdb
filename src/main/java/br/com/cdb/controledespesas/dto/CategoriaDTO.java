package br.com.cdb.controledespesas.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaDTO {

    @NotBlank(message = "O nome da categoria é obrigatório!")
    private String nome;
}
