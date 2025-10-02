package br.com.cdb.controledespesas.core.domain.usecase;

import br.com.cdb.controledespesas.core.domain.exception.BusinessRuleException;
import br.com.cdb.controledespesas.core.domain.model.Usuario;
import br.com.cdb.controledespesas.port.output.DespesaOutputPort;
import br.com.cdb.controledespesas.port.output.UsuarioOutputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioUseCaseTest {

    @Mock
    private UsuarioOutputPort usuarioOutputPort;

    @Mock
    private DespesaOutputPort despesaOutputPort;

    @InjectMocks
    private UsuarioUseCase usuarioUseCase;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Pedro");
    }

    @Test
    void deveSalvarUsuarioComSucesso() {
        when(usuarioOutputPort.salvarUsuario(usuario)).thenReturn(usuario);

        Usuario salvo = usuarioUseCase.salvarUsuario(usuario);

        assertNotNull(salvo);
        assertEquals("Pedro", salvo.getNome());
        verify(usuarioOutputPort, times(1)).salvarUsuario(usuario);
    }

    @Test
    void deveDeletarUsuarioSemDespesas() {
        when(usuarioOutputPort.buscarPorId(1L)).thenReturn(Optional.of(usuario));
        when(despesaOutputPort.existsByUsuario(1L)).thenReturn(false);

        usuarioUseCase.deletarUsuario(1L);

        verify(usuarioOutputPort, times(1)).deletarUsuario(1L);
    }

    @Test
    void naoDeveDeletarUsuarioQuandoNaoExiste() {
        when(usuarioOutputPort.buscarPorId(1L)).thenReturn(Optional.empty());

        BusinessRuleException exception = assertThrows(
                BusinessRuleException.class,
                () -> usuarioUseCase.deletarUsuario(1L)
        );

        assertEquals("Usuario não encontrada", exception.getMessage());
        verify(usuarioOutputPort, never()).deletarUsuario(any());
    }

    @Test
    void naoDeveDeletarUsuarioQuandoPossuiDespesas() {
        when(usuarioOutputPort.buscarPorId(1L)).thenReturn(Optional.of(usuario));
        when(despesaOutputPort.existsByUsuario(1L)).thenReturn(true);

        BusinessRuleException exception = assertThrows(
                BusinessRuleException.class,
                () -> usuarioUseCase.deletarUsuario(1L)
        );

        assertEquals("Não é possível remover o usuario, existem despesas vinculadas.", exception.getMessage());
        verify(usuarioOutputPort, never()).deletarUsuario(any());
    }

    @Test
    void deveAlterarUsuarioComSucesso() {
        when(usuarioOutputPort.buscarPorId(1L)).thenReturn(Optional.of(usuario));
        when(usuarioOutputPort.alterarInfoUsuario(usuario)).thenReturn(usuario);

        Usuario atualizado = usuarioUseCase.alterarInfoUsuario(usuario);

        assertNotNull(atualizado);
        assertEquals("Pedro", atualizado.getNome());
        verify(usuarioOutputPort, times(1)).alterarInfoUsuario(usuario);
    }

    @Test
    void naoDeveAlterarUsuarioQuandoNaoExiste() {
        when(usuarioOutputPort.buscarPorId(1L)).thenReturn(Optional.empty());

        BusinessRuleException exception = assertThrows(
                BusinessRuleException.class,
                () -> usuarioUseCase.alterarInfoUsuario(usuario)
        );

        assertEquals("Usuário não encontrado com id: 1", exception.getMessage());
        verify(usuarioOutputPort, never()).alterarInfoUsuario(any());
    }
}
