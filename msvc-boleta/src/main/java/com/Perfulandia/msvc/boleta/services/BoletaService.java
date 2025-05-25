package com.Perfulandia.msvc.boleta.services;

import com.Perfulandia.msvc.boleta.models.entities.Boleta;

import java.util.List;
import java.util.Optional;

public interface BoletaService {
    Boleta crear(Boleta boleta);
    List<Boleta> listar();
    Optional<Boleta> obtener(Long id);
    boolean eliminar(Long id);
}
