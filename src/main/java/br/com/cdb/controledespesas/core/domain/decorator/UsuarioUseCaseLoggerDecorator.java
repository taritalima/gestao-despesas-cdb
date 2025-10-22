package br.com.cdb.controledespesas.core.domain.decorator;

import br.com.cdb.controledespesas.core.domain.model.Usuario;
import br.com.cdb.controledespesas.port.input.UsuarioInputPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsuarioUseCaseLoggerDecorator implements UsuarioInputPort {

    private static final Logger log = LoggerFactory.getLogger(UsuarioUseCaseLoggerDecorator.class);
    private final UsuarioInputPort decorated;

    public UsuarioUseCaseLoggerDecorator(UsuarioInputPort decorated) {
        this.decorated = decorated;
    }

    @Override
    public Usuario salvarUsuario(Usuario usuario) {
        log.info("Salvando usuário: {}", usuario.getNome());
        Usuario result = decorated.salvarUsuario(usuario);
        log.info("Usuário salvo: {} (id={})", result.getNome(), result.getId());
        return result;
    }

    @Override
    public void deletarUsuario(Long id) {
        log.info("Deletando usuário id={}", id);
        decorated.deletarUsuario(id);
        log.info("Usuário deletado: id={}", id);
    }

    @Override
    public Usuario alterarInfoUsuario(Usuario usuario) {
        log.info("Alterando usuário id={}", usuario.getId());
        Usuario result = decorated.alterarInfoUsuario(usuario);
        log.info("Usuário alterado: {} (id={})", result.getNome(), result.getId());
        return result;
    }

}
