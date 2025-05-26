package com.Perfulandia.msvc.detalleBoleta.services;

import com.Perfulandia.msvc.detalleBoleta.models.entities.DetalleBoleta;
import com.Perfulandia.msvc.detalleBoleta.repositories.DetalleBoletaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleBoletaServiceImpl implements DetalleBoletaService {
    private final DetalleBoletaRepository repository;

    public DetalleBoletaServiceImpl(DetalleBoletaRepository repository) {
        this.repository = repository;
    }

    @Override
    public DetalleBoleta guardar(DetalleBoleta detalle) {
        return repository.save(detalle);
    }

    @Override
    public List<DetalleBoleta> listarPorBoleta(Long boletaId) {
        return repository.findByBoletaId(boletaId);
    }

    @Override
    public Optional<DetalleBoleta> obtener(Long id) {
        return repository.findById(id);
    }

    @Override
    public boolean eliminar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
