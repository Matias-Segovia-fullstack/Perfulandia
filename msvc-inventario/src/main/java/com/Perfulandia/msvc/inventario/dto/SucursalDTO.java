package com.Perfulandia.msvc.inventario.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SucursalDTO {
    private Long idSucursal;
    private String nombreSucursal;
    private String direccion;
}
