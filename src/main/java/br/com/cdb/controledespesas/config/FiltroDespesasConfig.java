package br.com.cdb.controledespesas.config;

import java.time.LocalDate;

public class FiltroDespesasConfig {

    private static FiltroDespesasConfig instance;

    private LocalDate dataInicioPadrao;
    private LocalDate dataFimPadrao;

    private FiltroDespesasConfig() {
        this.dataInicioPadrao = LocalDate.now().withDayOfMonth(1);
        this.dataFimPadrao = LocalDate.now();
    }

    public static FiltroDespesasConfig getInstance() {
        if (instance == null) {
            instance = new FiltroDespesasConfig();
        }
        return instance;
    }

    public LocalDate getDataInicioPadrao() {
        return dataInicioPadrao;
    }

    public LocalDate getDataFimPadrao() {
        return dataFimPadrao;
    }

    public void setDataInicioPadrao(LocalDate dataInicioPadrao) {
        this.dataInicioPadrao = dataInicioPadrao;
    }

    public void setDataFimPadrao(LocalDate dataFimPadrao) {
        this.dataFimPadrao = dataFimPadrao;
    }
}
