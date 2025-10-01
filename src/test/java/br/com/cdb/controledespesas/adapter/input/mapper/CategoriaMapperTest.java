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
    void deveConverterRequestParaDomain() {
        CategoriaRequest request = new CategoriaRequest();
        request.setNome("Salgados");

        Categoria domain = mapper.toDomain(request);

        assertThat(domain).isNotNull();
        assertThat(domain.getNome()).isEqualTo("Salgados");
    }
}
