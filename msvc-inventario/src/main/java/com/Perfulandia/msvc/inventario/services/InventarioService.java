package com.Perfulandia.msvc.inventario.services;


import com.Perfulandia.msvc.inventario.models.entities.Inventario;

import java.util.List;

public interface InventarioService {

    List<Inventario> findAll();
    Inventario findById(Long id);
    Inventario save(Inventario inventario);
}
