package com.Perfulandia.msvc.boleta.repositories;

import com.Perfulandia.msvc.boleta.models.entities.Boleta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoletaRepository extends JpaRepository<Boleta, Long> {
}
