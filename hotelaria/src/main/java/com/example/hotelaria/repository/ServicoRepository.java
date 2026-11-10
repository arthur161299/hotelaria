package com.example.hotelaria.repository;

import com.example.hotelaria.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoRepository
        extends JpaRepository<Servico, Long> {
}