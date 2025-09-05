package br.com.cdb.controledespesas.adapter.output.repository;

import br.com.cdb.controledespesas.adapter.input.mapper.CategoriaMapper;
import br.com.cdb.controledespesas.adapter.output.entity.CategoriaEntity;
import br.com.cdb.controledespesas.adapter.output.entity.DespesaEntity;
import br.com.cdb.controledespesas.core.domain.model.Categoria;
import br.com.cdb.controledespesas.port.output.CategoriaOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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

        String sql = "INSERT INTO categoria (nome) VALUES (?) RETURNING id, nome";
        CategoriaEntity savedEntity = jdbcTemplate.queryForObject(
                sql,
                new Object[]{entity.getNome()},
                (rs, rowNum) -> new CategoriaEntity(rs.getLong("id"), rs.getString("nome"))
        );

        return categoriaMapper.toDomain(savedEntity);
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
