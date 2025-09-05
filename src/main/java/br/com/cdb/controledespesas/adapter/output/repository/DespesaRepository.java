package br.com.cdb.controledespesas.adapter.output.repository;

import br.com.cdb.controledespesas.adapter.input.mapper.DespesaMapper;
import br.com.cdb.controledespesas.adapter.output.entity.DespesaEntity;
import br.com.cdb.controledespesas.core.domain.model.Despesa;
import br.com.cdb.controledespesas.port.output.DespesaOutputPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class DespesaRepository implements DespesaOutputPort {

    private final JdbcTemplate jdbcTemplate;
    private final DespesaMapper despesaMapper;

    public DespesaRepository(JdbcTemplate jdbcTemplate, DespesaMapper despesaMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.despesaMapper = despesaMapper;
    }

    @Override
    public Despesa salvarDespesa(Despesa despesa) {

        DespesaEntity entity = despesaMapper.toEntity(despesa);

        String sql = "INSERT INTO despesa (descricao, valor, categoria_id, usuario_id, pago_em, criado_em) VALUES (?,?,?,?,?,?)" +
                "RETURNING id, categoria_id, descricao, valor, pago_em, criado_em, usuario_id";


        DespesaEntity savedEntity = jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> new DespesaEntity(
                        rs.getLong("id"),
                        rs.getLong("categoria_id"),
                        rs.getString("descricao"),
                        rs.getBigDecimal("valor"),
                        rs.getDate("pago_em").toLocalDate(),
                        rs.getTimestamp("criado_em").toLocalDateTime(),
                        rs.getLong("usuario_id")
                ),
                entity.getDescricao(),
                entity.getValor(),
                entity.getCategoriaId(),
                entity.getUsuarioId(),
                java.sql.Date.valueOf(despesa.getPagoEm()),
                java.sql.Timestamp.valueOf(LocalDateTime.now())
        );

        return despesaMapper.toDomain(savedEntity);
    }

    @Override
    public void deletarDespesaPorId(Long id, Long usuarioId){
        String sql = "DELETE FROM despesa WHERE id = ? AND usuario_id = ?";
        jdbcTemplate.update(sql, id, usuarioId);
    }

    @Override
    public Despesa atualizarDespesa(Despesa despesa) {
        DespesaEntity entity = despesaMapper.toEntity(despesa);

        String sql = "UPDATE despesa SET descricao = ?, valor = ?, categoria_id = ?, pago_em = ? " +
                "WHERE id = ? AND usuario_id = ? " +
                "RETURNING id, categoria_id, descricao, valor, pago_em, criado_em, usuario_id";

        DespesaEntity updatedEntity = jdbcTemplate.queryForObject(
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
                entity.getDescricao(),
                entity.getValor(),
                entity.getCategoriaId(),
                java.sql.Date.valueOf(despesa.getPagoEm()),
                despesa.getId(),
                despesa.getUsuarioId()
        );
        return despesaMapper.toDomain(updatedEntity);
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
        String sql = "SELECT * FROM public.buscar_despesa_por_id_usuario(?, ?)";

        List<Despesa> resultados = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new Despesa(
                        rs.getLong("id"),
                        rs.getLong("usuario_id"),
                        rs.getLong("categoria_id"),
                        rs.getString("descricao"),
                        rs.getBigDecimal("valor"),
                        rs.getDate("pago_em").toLocalDate(),
                        rs.getTimestamp("criado_em").toLocalDateTime()
                ),
                id, usuarioId
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