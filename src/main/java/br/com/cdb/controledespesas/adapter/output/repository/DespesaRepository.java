package br.com.cdb.controledespesas.adapter.output.repository;

import br.com.cdb.controledespesas.adapter.output.entity.CategoriaEntity;
import br.com.cdb.controledespesas.adapter.output.entity.DespesaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DespesaRepository extends JpaRepository<DespesaEntity, Long>, JpaSpecificationExecutor<DespesaEntity> {
  List<DespesaEntity> findByUsuarioIdAndCategoriaAndPagoEmBetween(Long usuarioId, CategoriaEntity categoria, LocalDate de, LocalDate ate);
  Optional<DespesaEntity> findByIdAndUsuarioId(Long id, Long usuarioId);
  boolean existsByCategoria (CategoriaEntity categoria);
}