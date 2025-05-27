package com.Perfulandia.msvc.inventario.services;


import com.Perfulandia.msvc.inventario.models.entities.Inventario;

import java.util.List;
import java.util.Optional;

public interface InventarioService {

    Optional<Inventario> findByIdProducto (Long idProducto);
    Inventario actualizarStock(Long idProducto, int cantidad);
    Inventario save(Inventario inventario);
}
