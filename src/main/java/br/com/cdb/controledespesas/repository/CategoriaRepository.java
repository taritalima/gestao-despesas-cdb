package br.com.cdb.controledespesas.repository;

import br.com.cdb.controledespesas.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria,Long> {
    boolean existsByNome (String nome);
}
