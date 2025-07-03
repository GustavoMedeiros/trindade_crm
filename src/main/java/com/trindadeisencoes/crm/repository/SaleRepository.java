package com.trindadeisencoes.crm.repository;

import com.trindadeisencoes.crm.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.time.LocalDateTime;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByClientId(Long clienteId);
    List<Sale> findByProdutoId(Long produtoid);
    List<Sale> findByVendedorId(Long vendedorid);
    List<Sale> findByDataVendaBetween(LocalDateTime inicio, LocalDateTime fim);
}