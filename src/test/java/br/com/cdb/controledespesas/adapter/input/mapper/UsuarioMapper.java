package br.com.cdb.controledespesas.adapter.input.mapper;

import br.com.cdb.controledespesas.adapter.input.request.UsuarioRequest;
import br.com.cdb.controledespesas.adapter.input.response.UsuarioResponse;
import br.com.cdb.controledespesas.adapter.output.entity.UsuarioEntity;
import br.com.cdb.controledespesas.core.domain.model.Usuario;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class UsuarioMapperTest {

    private final UsuarioMapper mapper = Mappers.getMapper(UsuarioMapper.class);

    @Test
    void deveConverterDomainParaResponse() {
        Usuario usuario = new Usuario(1L, "Messi");

        UsuarioResponse response = mapper.toResponse(usuario);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getNome()).isEqualTo("Messi");
    }

    @Test
    void deveConverterEntityParaDomain() {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(2L);
        entity.setNome("Paola");

        Usuario domain = mapper.toDomain(entity);

        assertThat(domain).isNotNull();
        assertThat(domain.getId()).isEqualTo(2L);
        assertThat(domain.getNome()).isEqualTo("Paola");
    }

    @Test
    void deveConverterDomainParaEntity() {
        Usuario usuario = new Usuario(3L, "Vitor roque");

        UsuarioEntity entity = mapper.toEntity(usuario);

        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(3L);
        assertThat(entity.getNome()).isEqualTo("Vitor roque");
    }

    @Test
    void deveConverterRequestParaDomain() {
        UsuarioRequest request = new UsuarioRequest();
        request.setNome("Ana");

        Usuario domain = mapper.toDomain(request);

        assertThat(domain).isNotNull();
        assertThat(domain.getNome()).isEqualTo("Ana");
    }
}
