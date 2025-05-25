package com.Perfulandia.msvc.usuario.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Table(name="usuario")
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(nullable = false)
    @NotBlank(message = "El nombre es obligatorio")
    private String nombreUsuario;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Debe ingresar Rut")
    @Pattern(regexp = "^\\d{1,2}\\.?\\d{3}\\.?\\d{3}-[\\dkK]$", message = "El formato del RUT debe ser xx.xxx.xxx-x")
    private String rut;

}






