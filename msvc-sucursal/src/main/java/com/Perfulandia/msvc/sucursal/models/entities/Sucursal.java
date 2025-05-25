package com.Perfulandia.msvc.sucursal.models.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name="sucursal")
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSucursal;

    @Column(nullable = false)
    @NotBlank(message = "El nombre es obligatorio")
    private String nombreSucursal;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;
}
