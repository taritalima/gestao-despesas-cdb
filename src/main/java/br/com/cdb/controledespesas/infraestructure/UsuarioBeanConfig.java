package br.com.cdb.controledespesas.infraestructure;

import br.com.cdb.controledespesas.core.domain.decorator.UsuarioUseCaseLoggerDecorator;
import br.com.cdb.controledespesas.core.domain.usecase.UsuarioUseCase;
import br.com.cdb.controledespesas.port.input.UsuarioInputPort;
import br.com.cdb.controledespesas.port.output.DespesaOutputPort;
import br.com.cdb.controledespesas.port.output.UsuarioOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class UsuarioBeanConfig {

    @Bean
    public UsuarioInputPort usuarioUseCaseDecorator(
            UsuarioOutputPort usuarioOutputPort,
            DespesaOutputPort despesaOutputPort
    ) {
        UsuarioUseCase useCase = new UsuarioUseCase(usuarioOutputPort, despesaOutputPort);
        return new UsuarioUseCaseLoggerDecorator(useCase);
    }
}
