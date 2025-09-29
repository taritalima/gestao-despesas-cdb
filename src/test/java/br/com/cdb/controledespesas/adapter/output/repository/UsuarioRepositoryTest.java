package br.com.cdb.controledespesas.adapter.output.repository;

import br.com.cdb.controledespesas.adapter.input.mapper.UsuarioMapper;
import br.com.cdb.controledespesas.adapter.output.entity.UsuarioEntity;
import br.com.cdb.controledespesas.core.domain.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UsuarioRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;
    private UsuarioEntity entity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario(1L, "Tarita");
        entity = new UsuarioEntity(1L, "Tarita");
    }

    @Test
    void deveSalvarUsuario() throws Exception {
        when(usuarioMapper.toEntity(usuario)).thenReturn(entity);
        when(usuarioMapper.toDomain(any(UsuarioEntity.class))).thenReturn(usuario);

        when(jdbcTemplate.execute(any(ConnectionCallback.class)))
                .thenAnswer(invocation -> {
                    ConnectionCallback<Usuario> callback = invocation.getArgument(0);
                    Connection connection = mock(Connection.class);
                    CallableStatement cs = mock(CallableStatement.class);

                    when(connection.prepareCall(anyString())).thenReturn(cs);
                    when(cs.getLong(2)).thenReturn(1L);
                    when(cs.getString(3)).thenReturn("Tarita");

                    return callback.doInConnection(connection);
                });

        Usuario result = usuarioRepository.salvarUsuario(usuario);

        assertThat(result).isEqualTo(usuario);
        verify(usuarioMapper).toEntity(usuario);
        verify(usuarioMapper).toDomain(any(UsuarioEntity.class));
    }

    @Test
    void deveDeletarUsuario() {
        usuarioRepository.deletarUsuario(1L);

        verify(jdbcTemplate).update(anyString(), eq(1L));
    }

    @Test
    void deveBuscarPorIdERetornarUsuario() {
        when(jdbcTemplate.query(anyString(), any(Object[].class), ArgumentMatchers.<org.springframework.jdbc.core.RowMapper<Usuario>>any()))
                .thenReturn(List.of(usuario));

        Optional<Usuario> result = usuarioRepository.buscarPorId(1L);

        assertThat(result).isPresent().contains(usuario);
    }

    @Test
    void deveBuscarPorIdRetornarVazioSeNaoEncontrado() {
        when(jdbcTemplate.query(anyString(), any(Object[].class), ArgumentMatchers.<org.springframework.jdbc.core.RowMapper<Usuario>>any()))
                .thenReturn(List.of());

        Optional<Usuario> result = usuarioRepository.buscarPorId(1L);

        assertThat(result).isEmpty();
    }

   
}
