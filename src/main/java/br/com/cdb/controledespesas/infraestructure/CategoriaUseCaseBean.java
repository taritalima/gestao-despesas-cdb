package br.com.cdb.controledespesas.infraestructure;

import br.com.cdb.controledespesas.core.domain.usecase.CategoriaUseCase;
import br.com.cdb.controledespesas.port.output.CategoriaOutputPort;
import br.com.cdb.controledespesas.port.output.DespesaOutputPort;
import org.springframework.stereotype.Service;

@Service
public class CategoriaUseCaseBean extends CategoriaUseCase {

    public CategoriaUseCaseBean(CategoriaOutputPort categoriaOutputPort, DespesaOutputPort despesaOutputPort) {
        super(categoriaOutputPort, despesaOutputPort);
    }
}
