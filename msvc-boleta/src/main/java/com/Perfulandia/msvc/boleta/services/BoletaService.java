package com.Perfulandia.msvc.boleta.services;

import com.Perfulandia.msvc.boleta.models.entities.Boleta;

import java.util.List;

public interface BoletaService {
    List<Boleta> findAll();
    Boleta findById(Long id);
    Boleta save(Boleta boleta);
}

