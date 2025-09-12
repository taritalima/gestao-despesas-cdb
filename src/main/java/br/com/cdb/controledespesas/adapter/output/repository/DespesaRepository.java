package br.com.cdb.controledespesas.adapter.output.repository;

import br.com.cdb.controledespesas.adapter.input.mapper.DespesaMapper;
import br.com.cdb.controledespesas.adapter.output.entity.DespesaEntity;
import br.com.cdb.controledespesas.core.domain.model.Despesa;
import br.com.cdb.controledespesas.port.output.DespesaOutputPort;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
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

        return jdbcTemplate.execute((Connection connection) -> {
            String sql = "CALL pr_insert_despesa(?, ?, ?, ?, ?, ?, ?)";
            try (CallableStatement cs = connection.prepareCall(sql)) {

                cs.setLong(1, entity.getUsuarioId());
                cs.setString(2, entity.getDescricao());
                cs.setBigDecimal(3, entity.getValor());
                cs.setLong(4, entity.getCategoriaId());
                cs.setDate(5, java.sql.Date.valueOf(entity.getPagoEm()));


                cs.registerOutParameter(6, Types.BIGINT);
                cs.registerOutParameter(7, Types.TIMESTAMP);

                cs.execute();

                entity.setId(cs.getLong(6));
                entity.setCriadoEm(cs.getTimestamp(7).toLocalDateTime());

                return despesaMapper.toDomain(entity);
            }
        });
    }

    @Override
    public Despesa atualizarDespesa(Despesa despesa){
        DespesaEntity entity = despesaMapper.toEntity(despesa);

        return jdbcTemplate.execute((Connection connection) -> {
            String sql = "CALL public.pr_update_despesa(?, ?, ?, ?, ?, ?, ?)";

            try (CallableStatement cs = connection.prepareCall(sql)) {
                cs.setLong(1, entity.getId());
                cs.setLong(2, entity.getUsuarioId());
                cs.setString(3, entity.getDescricao());
                cs.setBigDecimal(4, entity.getValor());
                cs.setLong(5, entity.getCategoriaId());
                cs.setDate(6, java.sql.Date.valueOf(entity.getPagoEm()));

                cs.registerOutParameter(7, Types.TIMESTAMP);

                cs.execute();

                Timestamp criadoEm = cs.getTimestamp(7);
                if (criadoEm != null) {
                    entity.setCriadoEm(criadoEm.toLocalDateTime());
                }

                return despesaMapper.toDomain(entity);
            }
        });
}

    @Override
    public void deletarDespesaPorId(Long id, Long usuarioId){
        String sql = "CALL public.pr_delete_despesa(?, ?)";
        jdbcTemplate.update(sql, id, usuarioId);
    }

    @Override
    public List<Despesa> filtrarDespesas(Long usuarioId, Long categoriaId, LocalDate de, LocalDate ate) {

        String sql = "SELECT * FROM public.fn_filtrar_despesas(?, ?, ?, ?)";

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
        String sql = "SELECT public.fn_exists_categoria(?)";

        Boolean exists = jdbcTemplate.queryForObject(
                sql,
                Boolean.class,
                categoriaId
        );

        return Boolean.TRUE.equals(exists);
    }

    @Override
    public boolean existsByUsuario(Long usuarioId){
        String sql = "SELECT public.fn_exists_usuario(?)";

        Boolean exists = jdbcTemplate.queryForObject(
                sql,
                Boolean.class,
                usuarioId
        );

        return Boolean.TRUE.equals(exists);
    }
}