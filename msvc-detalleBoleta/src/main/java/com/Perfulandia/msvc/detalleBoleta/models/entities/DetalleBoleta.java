package com.Perfulandia.msvc.detalleBoleta.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Table(name = "detalles_boleta")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleBoleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long boletaId; // Relación lógica con boleta

    @NotNull
    private Long productoId; // Relación lógica con producto

    @NotNull
    @Positive
    private Integer cantidad;

    @NotNull
    @Positive
    private Double precioUnitario;

    public Double getSubtotal() {
        return cantidad * precioUnitario;
    }
}
