package br.com.fiap.mercado_da_vila.repository;

import br.com.fiap.mercado_da_vila.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    // A mágica do Spring Data JPA!
    // Todos os métodos básicos de CRUD (save, findById, findAll, deleteById)
    // já estão implementados.
}