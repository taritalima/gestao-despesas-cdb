package br.com.cdb.controledespesas.adapter.input.mapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {
    CategoriaMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper( CategoriaMapper.class);

}
