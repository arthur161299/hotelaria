package com.example.hotelaria.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reservas")
@Getter
@Setter
@NoArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataEntrada;

    private LocalDate dataSaida;

    private String status;

    private BigDecimal valorTotal;

    // Relacionamento N:1 com Hospede
    @ManyToOne
    @JoinColumn(name = "hospede_id", nullable = false)
    private Hospede hospede;

    // Relacionamento N:N com Quarto
    @ManyToMany
    @JoinTable(
        name = "reserva_quarto",
        joinColumns = @JoinColumn(name = "reserva_id"),
        inverseJoinColumns = @JoinColumn(name = "quarto_id")
    )
    private List<Quarto> quartos = new ArrayList<>();

    // Relacionamento N:N com Servico
    @ManyToMany
    @JoinTable(
        name = "reserva_servico",
        joinColumns = @JoinColumn(name = "reserva_id"),
        inverseJoinColumns = @JoinColumn(name = "servico_id")
    )
    private List<Servico> servicos = new ArrayList<>();

    // Relacionamento 1:N com Pagamento
    @OneToMany(mappedBy = "reserva")
    private List<Pagamento> pagamentos = new ArrayList<>();
}