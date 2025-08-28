package br.com.cdb.controledespesas.adapter.output.repository;

import br.com.cdb.controledespesas.adapter.input.mapper.DespesaMapper;
import br.com.cdb.controledespesas.adapter.output.entity.DespesaEntity;
import br.com.cdb.controledespesas.core.domain.model.Despesa;
import br.com.cdb.controledespesas.port.output.DespesaOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class DespesaRepositoryImpl implements DespesaOutputPort {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    DespesaMapper despesaMapper;

    @Override
    public Despesa salvarDespesa(Despesa despesa) {
        String sql = "INSERT INTO despesa (descricao, valor, categoria_id, usuario_id, pago_em, criado_em) VALUES (?,?,?,?,?,?)" +
                "RETURNING id, categoria_id, descricao, valor, pago_em, criado_em, usuario_id";


        DespesaEntity entity = jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> new DespesaEntity(
                        rs.getLong("id"),
                        rs.getLong("categoria_id"),
                        rs.getString("descricao"),
                        rs.getBigDecimal("valor"),
                        rs.getDate("pago_em").toLocalDate(),
                        rs.getTimestamp("criado_em").toLocalDateTime(),
                        rs.getLong("usuario_id")
                ),
                despesa.getDescricao(),
                despesa.getValor(),
                despesa.getCategoriaId(),
                despesa.getUsuarioId(),
                java.sql.Date.valueOf(despesa.getPagoEm()),
                java.sql.Timestamp.valueOf(LocalDateTime.now())
        );

        return despesaMapper.toDomain(entity);
    }
    @Override
    public void deletarDespesaPorId(Long id, Long usuarioId){
        String sql = "DELETE FROM despesa WHERE id = ? AND usuario_id = ?";
        jdbcTemplate.update(sql, id, usuarioId);
    }

    @Override
    public Despesa atualizarDespesa(Despesa despesa) {
        String sql = "UPDATE despesa SET descricao = ?, valor = ?, categoria_id = ?, pago_em = ? " +
                "WHERE id = ? AND usuario_id = ? RETURNING id, categoria_id, descricao, valor, pago_em, criado_em, usuario_id";

        DespesaEntity entity = jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> new DespesaEntity(
                        rs.getLong("id"),
                        rs.getLong("categoria_id"),
                        rs.getString("descricao"),
                        rs.getBigDecimal("valor"),
                        rs.getDate("pago_em").toLocalDate(),
                        rs.getTimestamp("criado_em").toLocalDateTime(),
                        rs.getLong("usuario_id")
                ),
                despesa.getDescricao(),
                despesa.getValor(),
                despesa.getCategoriaId(),
                java.sql.Date.valueOf(despesa.getPagoEm()),
                despesa.getId(),
                despesa.getUsuarioId()
        );

        return despesaMapper.toDomain(entity);
    }

    @Override
    public List<Despesa> filtrarDespesas(Long usuarioId, Long categoriaId, LocalDate de, LocalDate ate) {

        String sql = "SELECT * FROM despesa WHERE usuario_id = ? AND categoria_id = ? AND pago_em BETWEEN ? AND ?";

        List<DespesaEntity> resultados = jdbcTemplate.query(
                sql, (rs, rowNum) -> new DespesaEntity(
                        rs.getLong("id"),
                        rs.getLong("categoria_id"),
                        rs.getString("descricao"),
                        rs.getBigDecimal("valor"),
                        rs.getDate("pago_em").toLocalDate(),
                        rs.getTimestamp("criado_em").toLocalDateTime(),
                        rs.getLong("usuario_id")
                ),
                usuarioId,
                categoriaId,
                java.sql.Date.valueOf(de),
                java.sql.Date.valueOf(ate)
        );

        return resultados.stream()
                .map(despesaMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Despesa> buscarPorIdEUsuario(Long id, Long usuarioId) {
        String sql = "SELECT * FROM despesa WHERE id = ? AND usuario_id = ?";

        List<Despesa> resultados = jdbcTemplate.query(
                sql,
                new Object[]{id, usuarioId},
                (rs, rowNum) -> new Despesa(
                        rs.getLong("id"),
                        rs.getLong("usuario_id"),
                        rs.getLong("categoria_id"),
                        rs.getString("descricao"),
                        rs.getBigDecimal("valor"),
                        rs.getDate("pago_em").toLocalDate(),
                        rs.getTimestamp("criado_em").toLocalDateTime()
                )
        );

        return resultados.isEmpty() ? Optional.empty() : Optional.of(resultados.get(0));
    }

    @Override
    public boolean existsByCategoria(Long categoriaId){
        String sql = "SELECT 1 FROM despesa WHERE categoria_id = ? LIMIT 1";
        List<Integer> resultado = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> rs.getInt(1),
                categoriaId
        );
        return !resultado.isEmpty();
    }
}