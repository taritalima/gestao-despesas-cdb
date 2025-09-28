package br.com.cdb.controledespesas.adapter.input.mapper;

import br.com.cdb.controledespesas.adapter.input.request.DespesaRequest;
import br.com.cdb.controledespesas.adapter.input.response.DespesaResponse;
import br.com.cdb.controledespesas.adapter.output.entity.DespesaEntity;
import br.com.cdb.controledespesas.core.domain.model.Despesa;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-27T13:45:29-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class DespesaMapperImpl implements DespesaMapper {

    @Override
    public Despesa toDomain(DespesaRequest request) {
        if ( request == null ) {
            return null;
        }

        Despesa despesa = new Despesa();

        despesa.setCriadoEm( request.getCriadoEm() );
        despesa.setCategoriaId( request.getCategoriaId() );
        despesa.setUsuarioId( request.getUsuarioId() );
        despesa.setDescricao( request.getDescricao() );
        despesa.setValor( request.getValor() );
        despesa.setPagoEm( request.getPagoEm() );

        return despesa;
    }

    @Override
    public DespesaResponse toResponse(Despesa despesa) {
        if ( despesa == null ) {
            return null;
        }

        DespesaResponse despesaResponse = new DespesaResponse();

        despesaResponse.setId( despesa.getId() );
        despesaResponse.setCategoriaId( despesa.getCategoriaId() );
        despesaResponse.setDescricao( despesa.getDescricao() );
        despesaResponse.setValor( despesa.getValor() );
        despesaResponse.setPagoEm( despesa.getPagoEm() );
        despesaResponse.setUsuarioId( despesa.getUsuarioId() );

        return despesaResponse;
    }

    @Override
    public Despesa toDomain(DespesaEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Despesa despesa = new Despesa();

        despesa.setCriadoEm( entity.getCriadoEm() );
        despesa.setId( entity.getId() );
        despesa.setCategoriaId( entity.getCategoriaId() );
        despesa.setUsuarioId( entity.getUsuarioId() );
        despesa.setDescricao( entity.getDescricao() );
        despesa.setValor( entity.getValor() );
        despesa.setPagoEm( entity.getPagoEm() );

        return despesa;
    }

    @Override
    public DespesaEntity toEntity(Despesa domain) {
        if ( domain == null ) {
            return null;
        }

        DespesaEntity despesaEntity = new DespesaEntity();

        despesaEntity.setId( domain.getId() );
        despesaEntity.setCategoriaId( domain.getCategoriaId() );
        despesaEntity.setDescricao( domain.getDescricao() );
        despesaEntity.setValor( domain.getValor() );
        despesaEntity.setPagoEm( domain.getPagoEm() );
        despesaEntity.setCriadoEm( domain.getCriadoEm() );
        despesaEntity.setUsuarioId( domain.getUsuarioId() );

        return despesaEntity;
    }
}
