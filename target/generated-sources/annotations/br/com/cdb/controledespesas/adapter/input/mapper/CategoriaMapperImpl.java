package br.com.cdb.controledespesas.adapter.input.mapper;

import br.com.cdb.controledespesas.adapter.input.response.CategoriaResponse;
import br.com.cdb.controledespesas.adapter.output.entity.CategoriaEntity;
import br.com.cdb.controledespesas.core.domain.model.Categoria;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-27T21:53:54-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class CategoriaMapperImpl implements CategoriaMapper {

    @Override
    public CategoriaResponse toResponse(Categoria categoria) {
        if ( categoria == null ) {
            return null;
        }

        Long id = null;
        String nome = null;

        id = categoria.getId();
        nome = categoria.getNome();

        CategoriaResponse categoriaResponse = new CategoriaResponse( id, nome );

        return categoriaResponse;
    }

    @Override
    public Categoria toDomain(CategoriaEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Categoria categoria = new Categoria();

        categoria.setId( entity.getId() );
        categoria.setNome( entity.getNome() );

        return categoria;
    }

    @Override
    public CategoriaEntity toEntity(Categoria categoria) {
        if ( categoria == null ) {
            return null;
        }

        CategoriaEntity categoriaEntity = new CategoriaEntity();

        categoriaEntity.setId( categoria.getId() );
        categoriaEntity.setNome( categoria.getNome() );

        return categoriaEntity;
    }
}
