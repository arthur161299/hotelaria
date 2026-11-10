package com.example.hotelaria.repository;

import com.example.hotelaria.model.Hospede;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospedeRepository
        extends JpaRepository<Hospede, Long> {
}