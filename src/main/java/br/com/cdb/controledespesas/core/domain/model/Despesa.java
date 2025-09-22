package br.com.cdb.controledespesas.core.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Despesa {

    private Long id;
    private Long usuarioId;
    private Long categoriaId;
    private LocalDateTime criadoEm;
    private LocalDate pagoEm;
    private String descricao;
    private BigDecimal valor;

    public Despesa() {}


    public Despesa(Long id, Long usuarioId, Long categoriaid, String descricao, BigDecimal valor, LocalDate pagoEm, LocalDateTime criadoEm) {
        this.usuarioId = usuarioId;
        this.categoriaId = categoriaid;
        this.descricao = descricao;
        this.valor = valor;
        this.pagoEm = pagoEm;
        this.criadoEm = criadoEm;
        this.id = id;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }


    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getPagoEm() {
        return pagoEm;
    }

    public void setPagoEm(LocalDate pagoEm) {
        this.pagoEm = pagoEm;
    }
}
