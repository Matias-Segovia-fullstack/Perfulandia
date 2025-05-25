package com.Perfulandia.msvc.sucursal.repositories;

import com.Perfulandia.msvc.sucursal.models.entities.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Long> {
}
