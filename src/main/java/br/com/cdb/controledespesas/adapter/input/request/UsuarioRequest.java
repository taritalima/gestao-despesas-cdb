package br.com.cdb.controledespesas.adapter.input.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequest {
    private Long id;
    @NotBlank(message = "O nome é obrigatório!")
    private String nome;
}
