package br.com.cdb.controledespesas.port.input;

import br.com.cdb.controledespesas.core.domain.model.Usuario;

public interface UsuarioInputPort {
    Usuario salvarUsuario(Usuario usuario);
    void deletarUsuario(Long id);
    Usuario alterarInfoUsuario(Usuario usuario);
}
