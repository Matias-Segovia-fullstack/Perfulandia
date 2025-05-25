package com.Perfulandia.msvc.producto.repositories;

import com.Perfulandia.msvc.producto.models.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository <Producto,Long>{

}
