package com.Perfulandia.msvc.inventario.models.entities;

import com.Perfulandia.msvc.producto.models.entities.Producto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name="inventario")
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor

public class Inventario {
    @Id
    private Long idProducto;

    @OneToOne
    @MapsId
    @JoinColumn(name= "id_producto")
    private Producto producto;

    @Column(nullable = false)
    @NotNull(message = "El campo descripción stock no puede ser nulo")
    private Integer stockActual;


}
