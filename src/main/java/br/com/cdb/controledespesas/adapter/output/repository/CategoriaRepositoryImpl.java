package br.com.cdb.controledespesas.adapter.output.repository;

import br.com.cdb.controledespesas.adapter.input.mapper.CategoriaMapper;
import br.com.cdb.controledespesas.adapter.output.entity.CategoriaEntity;
import br.com.cdb.controledespesas.core.domain.model.Categoria;
import br.com.cdb.controledespesas.port.output.CategoriaOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoriaRepositoryImpl implements CategoriaOutputPort {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    CategoriaMapper categoriaMapper;

    public CategoriaRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Categoria salvarCategoria(Categoria categoria) {
        String sql = "INSERT INTO categoria (nome) VALUES (?) RETURNING id, nome";

        CategoriaEntity entity = jdbcTemplate.queryForObject(
                sql,
                new Object[]{categoria.getNome()},
                (rs, rowNum) -> new CategoriaEntity(rs.getLong("id"), rs.getString("nome"))
        );

        return categoriaMapper.toDomain(entity);
    }

    @Override
    public void deletarCategoria(Long categoriaId){
        String sql = "DELETE FROM categoria WHERE id = ?";
        jdbcTemplate.update(sql,categoriaId);
    }

    @Override
    public Optional<Categoria> buscarPorId(Long id) {
        String sql = "SELECT * FROM categoria WHERE id = ?";

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
        String sql = "SELECT * FROM categoria WHERE nome = ?";
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
}
