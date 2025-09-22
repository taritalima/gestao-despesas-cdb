package br.com.cdb.controledespesas.adapter.input.mapper;
import br.com.cdb.controledespesas.adapter.input.request.CategoriaRequest;
import br.com.cdb.controledespesas.adapter.input.response.CategoriaResponse;
import br.com.cdb.controledespesas.adapter.output.entity.CategoriaEntity;
import br.com.cdb.controledespesas.core.domain.model.Categoria;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {
    CategoriaMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper( CategoriaMapper.class);

    CategoriaResponse toResponse(Categoria categoria);
    Categoria toDomain(CategoriaEntity entity);
    CategoriaEntity toEntity(Categoria categoria);
    Categoria toDomain(CategoriaRequest request);
}
