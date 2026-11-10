package com.example.hotelaria.repository;

import com.example.hotelaria.model.Quarto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuartoRepository
        extends JpaRepository<Quarto, Long> {
}