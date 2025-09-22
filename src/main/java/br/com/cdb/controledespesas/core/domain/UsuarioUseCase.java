package br.com.cdb.controledespesas.core.domain;

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
    private static final Logger log = LoggerFactory.getLogger(UsuarioUseCase.class);

    public UsuarioUseCase(UsuarioOutputPort usuarioOutputPort, DespesaOutputPort despesaOutputPort) {
        this.usuarioOutputPort = usuarioOutputPort;
        this.despesaOutputPort = despesaOutputPort;
    }

    public Usuario salvarUsuario(Usuario usuario){
        Usuario salva = usuarioOutputPort.salvarUsuario(usuario);
        log.info("Usuario '{}' salva com sucesso", salva.getNome());
        return salva;
    }

    public  void deletarUsuario(Long usuarioId){
            Usuario usuario = usuarioOutputPort.buscarPorId(usuarioId)
                    .orElseThrow(() -> {
                        log.warn("Usuario não encontrada com id: {}", usuarioId);
                        return new BusinessRuleException("Usuario não encontrada");
                    });

            if (despesaOutputPort.existsByUsuario(usuario.getId())) {
                log.warn("Não é possível remover o usuario, existem despesas vinculadas do usuario: {}, com id {}", usuario.getNome(), usuario.getId());
                throw new BusinessRuleException("Não é possível remover o usuario, existem despesas vinculadas.");
            }
            usuarioOutputPort.deletarUsuario(usuario.getId());
            log.info("Usuario deletada com sucesso: {} (id={})", usuario.getNome(), usuario.getId());
    }

    public Usuario alterarInfoUsuario(Usuario usuario){
       usuarioOutputPort.buscarPorId(usuario.getId())
               .orElseThrow(() -> {
                   log.warn("Usuário não encontrado com id: {}", usuario.getId());
                   return new BusinessRuleException("Usuário não encontrado com id: " + usuario.getId());
               });
        Usuario usuarioAtualizado = usuarioOutputPort.alterarInfoUsuario(usuario);
        log.info("Usuario atualizado com sucesso: {} (id={})", usuarioAtualizado.getNome(), usuarioAtualizado.getId());
        return usuarioAtualizado;

    }
}
