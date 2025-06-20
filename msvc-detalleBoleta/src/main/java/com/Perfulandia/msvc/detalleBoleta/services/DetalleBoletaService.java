package com.Perfulandia.msvc.detalleBoleta.services;

import com.Perfulandia.msvc.detalleBoleta.models.entities.DetalleBoleta;

import java.util.List;
import java.util.Optional;

public interface DetalleBoletaService {
    DetalleBoleta guardar(DetalleBoleta detalle);
    List<DetalleBoleta> listarPorBoleta(Long idBoleta);
    Optional<DetalleBoleta> obtener(Long id);
    void borrar(Long id);
    DetalleBoleta actualizar(Long id, DetalleBoleta nuevoDetalle);
}

