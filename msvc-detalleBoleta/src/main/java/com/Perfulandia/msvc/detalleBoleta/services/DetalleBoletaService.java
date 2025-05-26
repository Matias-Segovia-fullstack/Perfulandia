package com.Perfulandia.msvc.detalleBoleta.services;

import com.Perfulandia.msvc.detalleBoleta.models.entities.DetalleBoleta;

import java.util.List;
import java.util.Optional;

public interface DetalleBoletaService {
    DetalleBoleta guardar(DetalleBoleta detalle);
    List<DetalleBoleta> listarPorBoleta(Long boletaId);
    Optional<DetalleBoleta> obtener(Long id);
    boolean eliminar(Long id);
}
