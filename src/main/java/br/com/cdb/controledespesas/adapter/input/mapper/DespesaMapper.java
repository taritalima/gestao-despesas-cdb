package br.com.cdb.controledespesas.adapter.input.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DespesaMapper {
    DespesaMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(DespesaMapper.class);
}
