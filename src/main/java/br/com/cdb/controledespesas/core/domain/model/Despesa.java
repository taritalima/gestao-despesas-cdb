package br.com.cdb.controledespesas.core.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Despesa {
    private Long usuarioId;
    private String categoriaNome;

    public Despesa(Long usuarioId, String categoriaNome, String descricao, BigDecimal valor, LocalDate pagoEm) {
        this.usuarioId = usuarioId;
        this.categoriaNome = categoriaNome;
        this.descricao = descricao;
        this.valor = valor;
        this.pagoEm = pagoEm;
    }

    private String descricao;
    private BigDecimal valor;

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getCategoriaNome() {
        return categoriaNome;
    }

    public void setCategoriaNome(String categoriaNome) {
        this.categoriaNome = categoriaNome;
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

    private LocalDate pagoEm;
}
