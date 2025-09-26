package br.com.cdb.controledespesas.infraestructure;

import br.com.cdb.controledespesas.core.domain.usecase.UsuarioUseCase;
import br.com.cdb.controledespesas.port.output.DespesaOutputPort;
import br.com.cdb.controledespesas.port.output.UsuarioOutputPort;
import org.springframework.stereotype.Service;

@Service
public class UsuarioUseCaseBean extends UsuarioUseCase {
    public UsuarioUseCaseBean(UsuarioOutputPort usuarioOutputPort, DespesaOutputPort despesaOutputPort) {
        super(usuarioOutputPort, despesaOutputPort);
    }
}