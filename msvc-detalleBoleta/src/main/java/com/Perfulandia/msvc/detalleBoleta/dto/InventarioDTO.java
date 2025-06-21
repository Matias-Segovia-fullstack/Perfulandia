package com.Perfulandia.msvc.detalleBoleta.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventarioDTO {
    private Long id;
    private Long idSucursal;
    private Long idProducto;
    private int stockActual;
}

