package com.example.hotelaria.repository;

import com.example.hotelaria.model.Reserva;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservaRepository
        extends JpaRepository<Reserva, Long> {

    // Buscar todas as reservas
    @EntityGraph(attributePaths = {"hospede"})
    @Override
    List<Reserva> findAll();

    // Buscar reserva pelo ID
    @EntityGraph(attributePaths = {"hospede"})
    @Override
    Optional<Reserva> findById(Long id);

    // Verificar conflitos de reservas
    @Query("""
        SELECT DISTINCT r FROM Reserva r
        JOIN r.quartos q
        WHERE q.id = :quartoId
        AND r.status <> 'CANCELADA'
        AND r.dataEntrada < :dataSaida
        AND r.dataSaida > :dataEntrada
    """)
    List<Reserva> buscarConflitos(
        @Param("quartoId") Long quartoId,
        @Param("dataEntrada") LocalDate dataEntrada,
        @Param("dataSaida") LocalDate dataSaida
    );
}