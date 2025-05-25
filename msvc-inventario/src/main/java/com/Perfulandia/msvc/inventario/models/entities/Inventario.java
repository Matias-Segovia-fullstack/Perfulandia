package com.Perfulandia.msvc.inventario.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name="inventario")
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor

public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStock;

    @Column(nullable = false)
    @NotBlank(message = "El campo descripción stock no puede ser nulo")
    private String descripcionStock;


}
