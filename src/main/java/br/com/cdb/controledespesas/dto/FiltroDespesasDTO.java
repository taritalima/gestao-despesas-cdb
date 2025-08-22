package br.com.cdb.controledespesas.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class FiltroDespesasDTO {

    @NotNull
    private Long usuarioId;

    @NotNull
    private Long categoriaId;
    @PastOrPresent(message="A data de inicio não pode ser futura")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate de;

    @PastOrPresent(message="A data final não pode ser futura")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate ate;

}
