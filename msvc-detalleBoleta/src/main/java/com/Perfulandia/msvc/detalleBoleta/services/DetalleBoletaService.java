package com.Perfulandia.msvc.detalleBoleta.services;

import com.Perfulandia.msvc.detalleBoleta.models.entities.DetalleBoleta;

import java.util.List;
import java.util.Optional;

public interface DetalleBoletaService {
    List<DetalleBoleta> findAll();
    DetalleBoleta guardar(DetalleBoleta detalle);
    List<DetalleBoleta> listarPorBoleta(Long idBoleta);
    DetalleBoleta obtener(Long id);
    void borrar(Long id);
    DetalleBoleta actualizar(Long id, DetalleBoleta nuevoDetalle);
}

