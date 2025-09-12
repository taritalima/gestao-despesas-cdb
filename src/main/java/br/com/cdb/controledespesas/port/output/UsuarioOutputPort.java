package br.com.cdb.controledespesas.port.output;

import br.com.cdb.controledespesas.core.domain.model.Usuario;

import java.util.Optional;

public interface UsuarioOutputPort {
    Usuario salvarUsuario(Usuario usuario);
    Optional<Usuario> buscarPorId(Long id);
    void deletarUsuario(Long id);
    Usuario alterarInfoUsuario(Usuario usuario);
}
