package br.com.cdb.controledespesas.infraestructure;


import br.com.cdb.controledespesas.core.domain.decorator.CategoriaUseCaseLoggerDecorator;
import br.com.cdb.controledespesas.core.domain.usecase.CategoriaUseCase;
import br.com.cdb.controledespesas.port.input.CategoriaInputPort;
import br.com.cdb.controledespesas.port.output.CategoriaOutputPort;
import br.com.cdb.controledespesas.port.output.DespesaOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoriaBeanConfig {

    @Bean
    public CategoriaInputPort categoriaUseCaseLoggerDecorator(CategoriaOutputPort categoriaOutputPort,
                                                              DespesaOutputPort despesaOutputPort) {
        CategoriaUseCase useCase = new CategoriaUseCase(categoriaOutputPort, despesaOutputPort);
        return new CategoriaUseCaseLoggerDecorator(useCase);
    }

}
