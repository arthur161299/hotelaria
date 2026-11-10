package com.example.hotelaria.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quartos")
@Getter
@Setter
@NoArgsConstructor
public class Quarto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numero;

    private String tipo;

    private BigDecimal valorDiaria;

    private String status;

    @ManyToMany(mappedBy = "quartos")
    private List<Reserva> reservas = new ArrayList<>();
}