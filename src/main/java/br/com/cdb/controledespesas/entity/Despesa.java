package br.com.cdb.controledespesas.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @Column(length = 200, nullable = false)
    private String descricao;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal  valor;

    @Column(name = "pago_em", nullable = false)
    private LocalDate pagoEm;

    @Column(name ="criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @PrePersist
    public void prePersist(){
        this.criadoEm = LocalDateTime.now();
    }

}
