package com.Perfulandia.msvc.boleta.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "boletas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Boleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long clienteId;

    @NotNull
    @DecimalMin("0.0")
    private Double montoTotal;

    @NotNull
    private LocalDateTime fechaEmision;
}
