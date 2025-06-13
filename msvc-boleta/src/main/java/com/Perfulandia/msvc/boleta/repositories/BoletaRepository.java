package com.Perfulandia.msvc.boleta.repositories;

import com.Perfulandia.msvc.boleta.models.entities.Boleta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoletaRepository extends JpaRepository<Boleta, Long> {
    List<Boleta> findByIdUsuario(Long idUsuario);
    List<Boleta> findByIdSucursal(Long idSucursal);
}

