package com.Perfulandia.msvc.detalleBoleta.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoletaDTO {
    private Long id;
    private Long idUsuario;
    private Long idSucursal;
    private Double total;
}
