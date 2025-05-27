package com.Perfulandia.msvc.boleta.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "boleta")
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class Boleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBoleta;

    @Column(nullable = false)
    @NotNull(message = "El ID del cliente es obligatorio")
    private Long idUsuario;

    @Column(nullable = false)
    @NotNull(message = "ID de sucursal obligatoria")
    private Long idSucursal;

    @Column(nullable = false)
    @NotNull(message = "El total es obligatorio")
    private Double total;

    private LocalDateTime fechaCreacion;
}


