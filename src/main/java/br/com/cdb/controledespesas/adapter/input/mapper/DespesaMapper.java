package br.com.cdb.controledespesas.adapter.input.mapper;

import br.com.cdb.controledespesas.adapter.input.request.DespesaRequest;
import br.com.cdb.controledespesas.adapter.input.response.DespesaResponse;
import br.com.cdb.controledespesas.adapter.output.entity.DespesaEntity;
import br.com.cdb.controledespesas.core.domain.model.Despesa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DespesaMapper {
    DespesaMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(DespesaMapper.class);

    Despesa toDomain(DespesaRequest request); // Request(dto de entrada) → Domain
    DespesaResponse toResponse(Despesa despesa); // Domain → Response(dto de saida)
    Despesa toDomain(DespesaEntity entity); // Entity → Domain
    DespesaEntity toEntity(Despesa domain);  // Domain → Entity
}
