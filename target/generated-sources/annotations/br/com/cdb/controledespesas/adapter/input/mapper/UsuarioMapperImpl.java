package br.com.cdb.controledespesas.adapter.input.mapper;

import br.com.cdb.controledespesas.adapter.input.request.UsuarioRequest;
import br.com.cdb.controledespesas.adapter.input.response.UsuarioResponse;
import br.com.cdb.controledespesas.adapter.output.entity.UsuarioEntity;
import br.com.cdb.controledespesas.core.domain.model.Usuario;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-27T13:45:29-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class UsuarioMapperImpl implements UsuarioMapper {

    @Override
    public UsuarioResponse toResponse(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }

        Long id = null;
        String nome = null;

        id = usuario.getId();
        nome = usuario.getNome();

        UsuarioResponse usuarioResponse = new UsuarioResponse( id, nome );

        return usuarioResponse;
    }

    @Override
    public Usuario toDomain(UsuarioEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Usuario usuario = new Usuario();

        usuario.setId( entity.getId() );
        usuario.setNome( entity.getNome() );

        return usuario;
    }

    @Override
    public UsuarioEntity toEntity(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }

        UsuarioEntity usuarioEntity = new UsuarioEntity();

        usuarioEntity.setId( usuario.getId() );
        usuarioEntity.setNome( usuario.getNome() );

        return usuarioEntity;
    }

    @Override
    public Usuario toDomain(UsuarioRequest request) {
        if ( request == null ) {
            return null;
        }

        Usuario usuario = new Usuario();

        usuario.setId( request.getId() );
        usuario.setNome( request.getNome() );

        return usuario;
    }
}
