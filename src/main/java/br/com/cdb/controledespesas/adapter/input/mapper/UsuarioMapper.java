package br.com.cdb.controledespesas.adapter.input.mapper;

import br.com.cdb.controledespesas.adapter.input.request.UsuarioRequest;
import br.com.cdb.controledespesas.adapter.input.response.UsuarioResponse;
import br.com.cdb.controledespesas.adapter.output.entity.UsuarioEntity;
import br.com.cdb.controledespesas.core.domain.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioResponse toResponse(Usuario usuario);
    Usuario toDomain(UsuarioEntity entity);
    UsuarioEntity toEntity(Usuario usuario);
    Usuario toDomain(UsuarioRequest request);
}
