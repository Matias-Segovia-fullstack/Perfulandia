package com.Perfulandia.msvc.detalleBoleta.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "detalle_boleta")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DetalleBoleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El campo idBoleta no puede ser nulo")
    private Long idBoleta;

    @NotNull(message = "El campo productoId no puede ser nulo")
    private Long productoId;

    @NotNull(message = "La cantidad no puede ser nula")
    private Integer cantidad;

    @NotNull(message = "El precio unitario no puede ser nulo")
    private Double precioUnitario;
}
