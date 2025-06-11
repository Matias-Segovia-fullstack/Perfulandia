package com.Perfulandia.msvc.inventario.repositories;
import java.util.List;


import com.Perfulandia.msvc.inventario.models.entities.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventarioRepository extends JpaRepository <Inventario,Long> {
    List<Inventario> findByIdSucursal(Long idSucursal);
    List<Inventario> findByIdProducto(Long idProducto);
    Optional<Inventario> findByIdProductAndSucursal(Long idSucursal, Long idProducto);

}
