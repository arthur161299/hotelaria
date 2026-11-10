package com.example.hotelaria.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hospedes")
@Getter
@Setter
@NoArgsConstructor
public class Hospede {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String cpf;

    private String email;

    private String telefone;

    // Relacionamento 1:N com Reserva
    @OneToMany(mappedBy = "hospede")
    private List<Reserva> reservas = new ArrayList<>();
}