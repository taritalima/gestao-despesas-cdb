package br.com.cdb.controledespesas.adapter.output.repository;

import br.com.cdb.controledespesas.adapter.input.mapper.DespesaMapper;
import br.com.cdb.controledespesas.adapter.output.entity.DespesaEntity;
import br.com.cdb.controledespesas.core.domain.model.Despesa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class DespesaRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private DespesaMapper despesaMapper;

    @InjectMocks
    private DespesaRepository despesaRepository;

    private Despesa despesa;
    private DespesaEntity entity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        despesa = new Despesa(1L, 10L, 20L, "Almoço",
                BigDecimal.valueOf(100), LocalDate.now(), LocalDateTime.now());

        entity = new DespesaEntity(1L, 20L, "Janta",
                BigDecimal.valueOf(100), LocalDate.now(), LocalDateTime.now(), 10L);
    }

    @Test
    void deveSalvarDespesa() throws Exception {
        when(despesaMapper.toEntity(despesa)).thenReturn(entity);
        when(despesaMapper.toDomain(any(DespesaEntity.class))).thenReturn(despesa);


        when(jdbcTemplate.execute(any(ConnectionCallback.class)))
                .thenAnswer(invocation -> {
                    ConnectionCallback<Despesa> callback = invocation.getArgument(0);
                    Connection connection = mock(Connection.class);
                    CallableStatement cs = mock(CallableStatement.class);

                    when(connection.prepareCall(anyString())).thenReturn(cs);
                    when(cs.getLong(6)).thenReturn(1L);
                    when(cs.getTimestamp(7)).thenReturn(Timestamp.valueOf(LocalDateTime.now()));

                    return callback.doInConnection(connection);
                });

        Despesa result = despesaRepository.salvarDespesa(despesa);

        assertThat(result).isEqualTo(despesa);
        verify(despesaMapper).toEntity(despesa);
        verify(despesaMapper).toDomain(any(DespesaEntity.class));
    }

    @Test
    void deveAtualizarDespesa() throws Exception {
        when(despesaMapper.toEntity(despesa)).thenReturn(entity);
        when(despesaMapper.toDomain(any(DespesaEntity.class))).thenReturn(despesa);

        when(jdbcTemplate.execute(any(ConnectionCallback.class)))
                .thenAnswer(invocation -> {
                    ConnectionCallback<Despesa> callback = invocation.getArgument(0);
                    Connection connection = mock(Connection.class);
                    CallableStatement cs = mock(CallableStatement.class);

                    when(connection.prepareCall(anyString())).thenReturn(cs);
                    when(cs.getTimestamp(7)).thenReturn(Timestamp.valueOf(LocalDateTime.now()));

                    return callback.doInConnection(connection);
                });

        Despesa result = despesaRepository.atualizarDespesa(despesa);

        assertThat(result).isEqualTo(despesa);
        verify(despesaMapper).toEntity(despesa);
        verify(despesaMapper).toDomain(any(DespesaEntity.class));
    }

    @Test
    void deveDeletarDespesa() {
        despesaRepository.deletarDespesaPorId(1L, 10L);

        verify(jdbcTemplate).update(anyString(), eq(1L), eq(10L));
    }

    @Test
    void deveFiltrarDespesas() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), any(), any(), any(), any()))
                .thenReturn(List.of(entity));
        when(despesaMapper.toDomain(entity)).thenReturn(despesa);

        List<Despesa> result = despesaRepository.filtrarDespesas(10L, 20L, LocalDate.now(), LocalDate.now());

        assertThat(result).containsExactly(despesa);
    }

    @Test
    void deveBuscarPorIdEUsuario() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), any(), any()))
                .thenReturn(List.of(despesa));

        Optional<Despesa> result = despesaRepository.buscarPorIdEUsuario(1L, 10L);

        assertThat(result).isPresent().contains(despesa);
    }

    
}
