package br.com.cdb.controledespesas.adapter.input.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaRequest {

    @NotBlank(message = "O nome da categoria é obrigatório!")
    private String nome;
}
