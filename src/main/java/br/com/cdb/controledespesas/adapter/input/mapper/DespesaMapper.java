package br.com.cdb.controledespesas.adapter.input.mapper;

import br.com.cdb.controledespesas.adapter.input.request.DespesaRequest;
import br.com.cdb.controledespesas.adapter.input.response.DespesaResponse;
import br.com.cdb.controledespesas.adapter.output.entity.DespesaEntity;
import br.com.cdb.controledespesas.core.domain.model.Despesa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DespesaMapper {
    DespesaMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(DespesaMapper.class);

    Despesa toDomain(DespesaRequest request);
    DespesaResponse toResponse(Despesa despesa);
    Despesa toDomain(DespesaEntity entity);
    DespesaEntity toEntity(Despesa domain);

}
