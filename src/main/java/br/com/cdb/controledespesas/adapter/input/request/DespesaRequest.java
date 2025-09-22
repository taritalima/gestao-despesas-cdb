package br.com.cdb.controledespesas.adapter.input.request;
import jakarta.validation.constraints.*;
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
public class DespesaRequest {

        @NotNull(message = "Categoria é obrigatória!")
        private Long categoriaId;

        @NotBlank(message = "Descrição é obrigatória!")
        @Size(min = 3, max = 200, message = "Descrição deve ter entre 3 e 200 caracteres!")
        private String descricao;

        @NotNull(message = "Valor é obrigatório")
        @Positive(message = "Valor deve ser maior que zero")
        private BigDecimal valor;

        @PastOrPresent(message = "A data de pagamento não pode estar no futuro!")
        private LocalDate pagoEm;

        private LocalDateTime criadoEm;

        private Long usuarioId;
    }

