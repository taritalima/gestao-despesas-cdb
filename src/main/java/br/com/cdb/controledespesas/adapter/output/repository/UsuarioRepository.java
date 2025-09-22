package br.com.cdb.controledespesas.adapter.output.repository;

import br.com.cdb.controledespesas.adapter.input.mapper.UsuarioMapper;
import br.com.cdb.controledespesas.adapter.output.entity.UsuarioEntity;
import br.com.cdb.controledespesas.core.domain.model.Usuario;
import br.com.cdb.controledespesas.port.output.UsuarioOutputPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepository  implements UsuarioOutputPort {

    private final JdbcTemplate jdbcTemplate;
    private final UsuarioMapper usuarioMapper;

    public UsuarioRepository(JdbcTemplate jdbcTemplate, UsuarioMapper usuarioMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public Usuario salvarUsuario(Usuario usuario) {

        UsuarioEntity entity = usuarioMapper.toEntity(usuario);
        return jdbcTemplate.execute((Connection connection) -> {
            String sql = "CALL public.pr_insert_usuario(?, ?, ?)";
            try(CallableStatement cs = connection.prepareCall(sql)){
                cs.setString(1, entity.getNome());

                cs.registerOutParameter(2, Types.BIGINT);
                cs.registerOutParameter(3, Types.VARCHAR);
                cs.execute();


                entity.setId(cs.getLong(2));
                entity.setNome(cs.getString(3));

                return usuarioMapper.toDomain(entity);
            }
        });
    }

    @Override
    public void deletarUsuario(Long usuarioId) {
        String sql = "CALL public.pr_deletar_usuario(?)";
        jdbcTemplate.update(sql, usuarioId);
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        String sql = "SELECT * FROM public.fn_buscar_usuario_por_id(?)";

        List<Usuario> usuarios = jdbcTemplate.query(
                sql,
                new Object[]{id},
                (rs, rowNum) -> new Usuario(
                        rs.getLong("id"),
                        rs.getString("nome")
                )
        );

        return usuarios.stream().findFirst();
    }

    @Override
    public Usuario alterarInfoUsuario(Usuario usuario) {
        UsuarioEntity entity = usuarioMapper.toEntity(usuario);

        return jdbcTemplate.execute((Connection connection) -> {
            String sql = "CALL public.pr_update_usuario(?, ?, ?)";
            try (CallableStatement cs = connection.prepareCall(sql)) {

                cs.setLong(1, entity.getId());
                cs.setString(2, entity.getNome());

                cs.registerOutParameter(3, Types.VARCHAR);

                cs.execute();

                entity.setNome(cs.getString(3));

                return usuarioMapper.toDomain(entity);
            }
        });

    }
}