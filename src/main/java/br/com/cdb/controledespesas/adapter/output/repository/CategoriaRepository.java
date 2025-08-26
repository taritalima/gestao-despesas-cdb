package br.com.cdb.controledespesas.adapter.output.repository;

import br.com.cdb.controledespesas.adapter.output.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaEntity,Long> {
    boolean existsByNome (String nome);
}
