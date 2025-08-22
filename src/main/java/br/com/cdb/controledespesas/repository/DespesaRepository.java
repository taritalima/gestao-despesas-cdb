package br.com.cdb.controledespesas.repository;

import br.com.cdb.controledespesas.entity.Categoria;
import br.com.cdb.controledespesas.entity.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long>, JpaSpecificationExecutor<Despesa> {
  List<Despesa> findByUsuarioIdAndCategoriaAndPagoEmBetween(Long usuarioId, Categoria categoria, LocalDate de, LocalDate ate);
  Optional<Despesa> findByIdAndUsuarioId(Long id, Long usuarioId);
  boolean existsByCategoria (Categoria categoria);
}