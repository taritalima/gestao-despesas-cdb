package br.com.cdb.controledespesas.adapter.input.mapper;

import br.com.cdb.controledespesas.adapter.input.request.CategoriaRequest;
import br.com.cdb.controledespesas.adapter.input.response.CategoriaResponse;
import br.com.cdb.controledespesas.adapter.output.entity.CategoriaEntity;
import br.com.cdb.controledespesas.core.domain.model.Categoria;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CategoriaMapperTest {

    private final CategoriaMapper mapper = CategoriaMapper.INSTANCE;

    @Test
    void deveConverterDomainParaResponse() {
        Categoria categoria = new Categoria(1L, "Bebidas");

        CategoriaResponse response = mapper.toResponse(categoria);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getNome()).isEqualTo("Bebidas");
    }

    @Test
    void deveConverterEntityParaDomain() {
        CategoriaEntity entity = new CategoriaEntity();
        entity.setId(2L);
        entity.setNome("Massa");

        Categoria domain = mapper.toDomain(entity);

        assertThat(domain).isNotNull();
        assertThat(domain.getId()).isEqualTo(2L);
        assertThat(domain.getNome()).isEqualTo("Massa");
    }

    @Test
    void deveConverterDomainParaEntity() {
        Categoria domain = new Categoria(3L, "Doces");

        CategoriaEntity entity = mapper.toEntity(domain);

        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(3L);
        assertThat(entity.getNome()).isEqualTo("Doces");
    }

    @Test
    void deveConverterRequestParaDomain() {
        CategoriaRequest request = new CategoriaRequest();
        request.setNome("Salgados");

        Categoria domain = mapper.toDomain(request);

        assertThat(domain).isNotNull();
        assertThat(domain.getNome()).isEqualTo("Salgados");
    }
}
