package com.Perfulandia.msvc.boleta.services;

import com.Perfulandia.msvc.boleta.models.entities.Boleta;

import java.util.List;

public interface BoletaService {
    List<Boleta> findAll();
    Boleta findById(Long id);
    Boleta save(Boleta boleta);
    List<Boleta> findByIdUsuario(Long idUsuario);
    List<Boleta> findByIdSucursal(Long idSucursal);
    Boleta actualizar(Long id, Boleta nuevaBoleta);
    void borrar(Long id);
}

