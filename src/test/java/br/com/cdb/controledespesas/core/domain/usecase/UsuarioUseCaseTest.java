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

    
}
