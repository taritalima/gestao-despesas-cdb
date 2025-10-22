package br.com.cdb.controledespesas.core.domain.usecase;

import br.com.cdb.controledespesas.core.domain.exception.BusinessRuleException;
import br.com.cdb.controledespesas.core.domain.model.Usuario;
import br.com.cdb.controledespesas.port.input.UsuarioInputPort;
import br.com.cdb.controledespesas.port.output.DespesaOutputPort;
import br.com.cdb.controledespesas.port.output.UsuarioOutputPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsuarioUseCase implements UsuarioInputPort {

    private final UsuarioOutputPort usuarioOutputPort;
    private final DespesaOutputPort despesaOutputPort;

    public UsuarioUseCase(UsuarioOutputPort usuarioOutputPort, DespesaOutputPort despesaOutputPort) {
        this.usuarioOutputPort = usuarioOutputPort;
        this.despesaOutputPort = despesaOutputPort;
    }


    @Override
    public Usuario salvarUsuario(Usuario usuario){
        return usuarioOutputPort.salvarUsuario(usuario);
    }

    @Override
    public void deletarUsuario(Long usuarioId){
        Usuario usuario = usuarioOutputPort.buscarPorId(usuarioId)
                .orElseThrow(() -> new BusinessRuleException("Usuario não encontrado"));

        if (despesaOutputPort.existsByUsuario(usuario.getId())) {
            throw new BusinessRuleException("Não é possível remover o usuario, existem despesas vinculadas.");
        }
        usuarioOutputPort.deletarUsuario(usuario.getId());
    }

    @Override
    public Usuario alterarInfoUsuario(Usuario usuario){
        usuarioOutputPort.buscarPorId(usuario.getId())
                .orElseThrow(() -> new BusinessRuleException("Usuário não encontrado com id: " + usuario.getId()));

        return usuarioOutputPort.alterarInfoUsuario(usuario);
    }
}

