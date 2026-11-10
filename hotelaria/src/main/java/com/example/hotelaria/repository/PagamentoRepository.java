package com.example.hotelaria.repository;

import com.example.hotelaria.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagamentoRepository
        extends JpaRepository<Pagamento, Long> {

    List<Pagamento> findByReservaId(Long reservaId);
}