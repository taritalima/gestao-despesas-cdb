package br.com.cdb.controledespesas.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
public class DespesaDTO {

        @NotNull(message = "Categoria é obrigatória!")
        private Long categoriaId;

        @NotBlank(message = "Descriação é obrigatória!")
        @Size(min = 3, max = 200, message = "Descrição deve ter entre 3 e 200 caracteres!")
        private String descricao;

        @NotNull(message = "Valor é obrigatório")
        @Positive(message = "Valor deve ser maior que zero")
        private BigDecimal valor;

        @PastOrPresent(message = "A data de pagamento não pode estar no futuro!")
        private LocalDate pagoEm;


        private LocalDateTime criadoEm;

        @Schema(hidden = true)
        private Long usuarioId;
    }

