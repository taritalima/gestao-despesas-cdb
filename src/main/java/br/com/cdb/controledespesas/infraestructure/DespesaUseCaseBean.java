package br.com.cdb.controledespesas.infraestructure;

import br.com.cdb.controledespesas.adapter.input.mapper.DespesaMapper;
import br.com.cdb.controledespesas.core.domain.usecase.DespesaUseCase;
import br.com.cdb.controledespesas.port.output.CategoriaOutputPort;
import br.com.cdb.controledespesas.port.output.DespesaOutputPort;
import org.springframework.stereotype.Service;

@Service
public class DespesaUseCaseBean extends DespesaUseCase {

    public DespesaUseCaseBean(CategoriaOutputPort categoriaOutputPort, DespesaOutputPort despesaOutputPort, DespesaMapper despesaMapper) {
        super(categoriaOutputPort, despesaOutputPort, despesaMapper);
    }

}
