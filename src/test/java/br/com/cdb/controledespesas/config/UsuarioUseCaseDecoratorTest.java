package br.com.cdb.controledespesas.config;

import br.com.cdb.controledespesas.core.domain.decorator.UsuarioUseCaseLoggerDecorator;
import br.com.cdb.controledespesas.core.domain.model.Usuario;
import br.com.cdb.controledespesas.core.domain.usecase.UsuarioUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
public class UsuarioUseCaseDecoratorTest {

    private UsuarioUseCase delegate;
    private UsuarioUseCaseLoggerDecorator decorator;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        delegate = mock(UsuarioUseCase.class);
        decorator = new UsuarioUseCaseLoggerDecorator(delegate);

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Pedro");
    }

    @Test
    void salvarUsuario_deveDelegar() {
        when(delegate.salvarUsuario(usuario)).thenReturn(usuario);

        Usuario resultado = decorator.salvarUsuario(usuario);

        assertEquals(usuario, resultado);
        verify(delegate).salvarUsuario(usuario);
    }

    @Test
    void deletarUsuario_deveDelegar() {
        doNothing().when(delegate).deletarUsuario(1L);

        decorator.deletarUsuario(1L);

        verify(delegate).deletarUsuario(1L);
    }

    @Test
    void alterarUsuario_deveDelegar() {
        when(delegate.alterarInfoUsuario(usuario)).thenReturn(usuario);

        Usuario resultado = decorator.alterarInfoUsuario(usuario);

        assertEquals(usuario, resultado);
        verify(delegate).alterarInfoUsuario(usuario);
    }
}
