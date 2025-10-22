package br.com.cdb.controledespesas.infraestructure;


import br.com.cdb.controledespesas.adapter.input.mapper.DespesaMapper;
import br.com.cdb.controledespesas.core.domain.decorator.DespesaUseCaseLoggerDecorator;
import br.com.cdb.controledespesas.core.domain.usecase.DespesaUseCase;
import br.com.cdb.controledespesas.port.input.DespesaInputPort;
import br.com.cdb.controledespesas.port.output.CategoriaOutputPort;
import br.com.cdb.controledespesas.port.output.DespesaOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DespesaBeanConfig {

    @Bean
    public DespesaInputPort despesaUseCaseDecorator(
            CategoriaOutputPort categoriaOutputPort,
            DespesaOutputPort despesaOutputPort,
            DespesaMapper despesaMapper
    ) {
        DespesaUseCase useCase = new DespesaUseCase(categoriaOutputPort, despesaOutputPort, despesaMapper);

        return new DespesaUseCaseLoggerDecorator(useCase);
    }
}
