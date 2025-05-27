package com.Perfulandia.msvc.inventario.repositories;

import com.Perfulandia.msvc.inventario.models.entities.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventarioRepository extends JpaRepository <Inventario,Long> {
    Optional<Inventario> findByProduct_Id(Long IdProducto);
}
