package com.Perfulandia.msvc.inventario.services;


import com.Perfulandia.msvc.inventario.models.entities.Inventario;

import java.util.List;
import java.util.Optional;

public interface InventarioService {

    List<Inventario> findAll();
    Inventario findById(Long id);
    Inventario save(Inventario inventario);
    List<Inventario> findByIdSucursal(Long idSucursal);
    List<Inventario> findByIdProducto(Long idProducto);
    Inventario findByProductoAndSucursal(Long idSucursal, Long idProducto);
    Inventario actualizarStock(Long idSucursal, Long idProducto, int nuevoStock);
    void borrar(Long id);}
