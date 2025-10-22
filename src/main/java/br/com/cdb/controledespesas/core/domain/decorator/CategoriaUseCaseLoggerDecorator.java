package br.com.cdb.controledespesas.core.domain.decorator;

import br.com.cdb.controledespesas.core.domain.model.Categoria;
import br.com.cdb.controledespesas.port.input.CategoriaInputPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CategoriaUseCaseLoggerDecorator implements CategoriaInputPort {

    private static final Logger log = LoggerFactory.getLogger(CategoriaUseCaseLoggerDecorator.class);
    private final CategoriaInputPort decorated;

    public CategoriaUseCaseLoggerDecorator(CategoriaInputPort decorated) {
        this.decorated = decorated;
    }

    @Override
    public Categoria salvarCategoria(Categoria categoria) {
        log.info("Salvando categoria: {}", categoria.getNome());
        Categoria result = decorated.salvarCategoria(categoria);
        log.info("Categoria salva: {} (id={})", result.getNome(), result.getId());
        return result;
    }

    @Override
    public void deletarCategoria(Long id) {
        log.info("Deletando categoria id={}", id);
        decorated.deletarCategoria(id);
        log.info("Categoria deletada com sucesso: id={}", id);
    }

    @Override
    public Categoria buscarCategoriaId(Long id) {
        log.info("Buscando categoria id={}", id);
        Categoria result = decorated.buscarCategoriaId(id);
        log.info("Categoria encontrada: {} (id={})", result.getNome(), result.getId());
        return result;
    }

    @Override
    public List<Categoria> buscarTodasCategorias() {
        log.info("Buscando todas as categorias");
        List<Categoria> result = decorated.buscarTodasCategorias();
        log.info("Total de categorias encontradas: {}", result.size());
        return result;
    }
}