package br.com.cdb.controledespesas.adapter.input.mapper;

import br.com.cdb.controledespesas.adapter.input.request.DespesaRequest;
import br.com.cdb.controledespesas.adapter.input.response.DespesaResponse;
import br.com.cdb.controledespesas.adapter.output.entity.DespesaEntity;
import br.com.cdb.controledespesas.core.domain.model.Despesa;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DespesaMapperTest {

    private final DespesaMapper mapper = DespesaMapper.INSTANCE;

    @Test
    void deveConverterRequestParaDomain() {
        DespesaRequest request = new DespesaRequest();
        request.setDescricao("Compra de coca cola 500ml");
        request.setValor(new BigDecimal("8.00"));

        Despesa domain = mapper.toDomain(request);

        assertThat(domain).isNotNull();
        assertThat(domain.getDescricao()).isEqualTo("Compra de coca cola 500ml");
        assertThat(domain.getValor()).isEqualTo(new BigDecimal("8.00"));
    }

    @Test
    void deveConverterDomainParaResponse() {
        Despesa domain = new Despesa(1L, 10L, 1L,
                "almoço: bife acebolado, arroz, feijão", BigDecimal.valueOf(40.00),
                LocalDate.of(2025,9,29),
                LocalDateTime.now()
        );

        DespesaResponse response = mapper.toResponse(domain);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getUsuarioId()).isEqualTo(10L);
        assertThat(response.getCategoriaId()).isEqualTo(1L);
        assertThat(response.getDescricao()).isEqualTo("almoço: bife acebolado, arroz, feijão");
    }

    @Test
    void deveConverterEntityParaDomain() {
        DespesaEntity entity = new DespesaEntity();
        entity.setId(2L);
        entity.setDescricao("Doces");
        entity.setValor(new BigDecimal("10.00"));

        Despesa domain = mapper.toDomain(entity);

        assertThat(domain).isNotNull();
        assertThat(domain.getId()).isEqualTo(2L);
        assertThat(domain.getDescricao()).isEqualTo("Doces");
        assertThat(domain.getValor()).isEqualTo(new BigDecimal("10.00"));
    }

    @Test
    void deveConverterDomainParaEntity() {
        Despesa domain = new Despesa();
        domain.setId(10L);
        domain.setCategoriaId(2L);
        domain.setDescricao("Croissant de chocolate");
        domain.setValor(new BigDecimal("7.00"));
        domain.setPagoEm(LocalDate.of(2025, 9, 30));
        domain.setUsuarioId(5L);

        DespesaEntity entity = mapper.toEntity(domain);

        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(10L);
        assertThat(entity.getCategoriaId()).isEqualTo(2L);
        assertThat(entity.getDescricao()).isEqualTo("Croissant de chocolate");
        assertThat(entity.getValor()).isEqualByComparingTo(new BigDecimal("7.00"));
        assertThat(entity.getPagoEm()).isEqualTo(LocalDate.of(2025, 9, 30));
        assertThat(entity.getUsuarioId()).isEqualTo(5L);
    }


}
