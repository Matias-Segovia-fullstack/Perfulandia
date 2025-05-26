package com.Perfulandia.msvc.detalleBoleta.repositories;

import com.Perfulandia.msvc.detalleBoleta.models.entities.DetalleBoleta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleBoletaRepository extends JpaRepository<DetalleBoleta, Long> {
    List<DetalleBoleta> findByBoletaId(Long boletaId);
}
