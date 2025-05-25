package com.Perfulandia.msvc.producto.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name="producto")
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor

public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;

    @Column(nullable = false)
    @NotBlank(message = "El campo nombre producto no puede ser nulo")
    private String nombreProducto;

    @Column(nullable = false)
    @NotNull(message = "El campo precio producto no puede ser nulo")
    private Integer precioProducto;

}
