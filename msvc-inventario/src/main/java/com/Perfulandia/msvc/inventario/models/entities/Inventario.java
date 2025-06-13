package com.Perfulandia.msvc.inventario.models.entities;

import com.Perfulandia.msvc.producto.models.entities.Producto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name="inventario")
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor

public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInventario;

    @Column(nullable = false)
    @NotNull(message = "El ID del producto es obligatorio")
    private Long idProducto;

    @Column(nullable = false)
    @NotNull(message = "El ID de la sucursal es obligatorio")
    private Long idSucursal;

    @Column(nullable = false)
    @NotNull(message = "El campo descripción stock no puede ser nulo")
    private Integer stockActual;


}

