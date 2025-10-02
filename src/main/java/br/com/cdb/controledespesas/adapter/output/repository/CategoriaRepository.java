package br.com.cdb.controledespesas.adapter.output.repository;

import br.com.cdb.controledespesas.adapter.input.mapper.CategoriaMapper;
import br.com.cdb.controledespesas.adapter.output.entity.CategoriaEntity;
import br.com.cdb.controledespesas.core.domain.model.Categoria;
import br.com.cdb.controledespesas.port.output.CategoriaOutputPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Repository
public class CategoriaRepository implements CategoriaOutputPort {

    private final JdbcTemplate jdbcTemplate;
    private final CategoriaMapper categoriaMapper;

    public CategoriaRepository(JdbcTemplate jdbcTemplate, CategoriaMapper categoriaMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.categoriaMapper = categoriaMapper;
    }

    @Override
    public Categoria salvarCategoria(Categoria categoria) {

        CategoriaEntity entity = categoriaMapper.toEntity(categoria);

        return jdbcTemplate.execute((Connection connection) -> {
            String sql = "CALL public.pr_insert_categoria(?, ?, ?)";

            try(CallableStatement cs = connection.prepareCall(sql)){
                cs.setString(1, entity.getNome());

                cs.registerOutParameter(2, Types.BIGINT);
                cs.registerOutParameter(3, Types.VARCHAR);
                cs.execute();


                entity.setId(cs.getLong(2));
                entity.setNome(cs.getString(3));
                return categoriaMapper.toDomain(entity);
            }
        });
    }

    @Override
    public void deletarCategoria(Long categoriaId){
        String sql = "CALL public.pr_delete_categoria(?)";
        jdbcTemplate.update(sql,categoriaId);
    }

    @Override
    public Optional<Categoria> buscarPorId(Long id) {
        String sql = "SELECT * FROM public.fn_buscar_categoria_por_id(?)";

        List<Categoria> categorias = jdbcTemplate.query(
                sql,
                new Object[]{id},
                (rs, rowNum) -> new Categoria(
                        rs.getLong("id"),
                        rs.getString("nome")
                )
        );

        return categorias.stream().findFirst();
    }

    @Override
    public Optional<Categoria> buscarPorName(String nome){
        String sql = "SELECT * FROM public.fn_buscar_categoria_por_nome(?)";
        List<Categoria> categorias = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new Categoria(
                        rs.getLong("id"),
                        rs.getString("nome")
                ),
                nome
        );

        return categorias.stream().findFirst();
    }

    @Override
    public List<Categoria> buscarTodasCategoria(){
        String sql = "SELECT * FROM public.fn_buscar_todas_categoria()";
        List<Categoria> categorias = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new Categoria(
                        rs.getLong("id"),
                        rs.getString("nome")
                )
        );
        return categorias;
    }
}
