package com.trindadeisencoes.crm.repository;

import com.trindadeisencoes.crm.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findByNameContainingIgnoreCase(String nome);

    @Query("SELECT c FROM Client c JOIN FETCH c.vendedor WHERE c.id = :id")
    Optional<Client> findByIdWithVendedor(@Param("id") Long id);
}