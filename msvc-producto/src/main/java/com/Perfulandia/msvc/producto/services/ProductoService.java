package com.Perfulandia.msvc.producto.services;

import com.Perfulandia.msvc.producto.models.entities.Producto;

import java.util.List;

public interface ProductoService {

    List<Producto> findAll();
    Producto findById(Long id);
    Producto save(Producto producto);
    Producto actualizar(Long id, Producto nuevoProducto);
    void borrar(Long id);

}
