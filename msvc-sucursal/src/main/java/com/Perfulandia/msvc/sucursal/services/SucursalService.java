package com.Perfulandia.msvc.sucursal.services;

import com.Perfulandia.msvc.sucursal.models.entities.Sucursal;

import java.util.List;

public interface SucursalService {

    List<Sucursal> findAll();
    Sucursal findById(Long id);
    Sucursal save(Sucursal sucursal);
}
