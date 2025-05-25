package com.Perfulandia.msvc.boleta.services;

import com.Perfulandia.msvc.boleta.models.entities.Boleta;
import com.Perfulandia.msvc.boleta.repositories.BoletaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoletaServiceImpl implements BoletaService {
    private final BoletaRepository boletaRepository;

    public BoletaServiceImpl(BoletaRepository boletaRepository) {
        this.boletaRepository = boletaRepository;
    }

    @Override
    public Boleta crear(Boleta boleta) {
        return boletaRepository.save(boleta);
    }

    @Override
    public List<Boleta> listar() {
        return boletaRepository.findAll();
    }

    @Override
    public Optional<Boleta> obtener(Long id) {
        return boletaRepository.findById(id);
    }

    @Override
    public boolean eliminar(Long id) {
        if (boletaRepository.existsById(id)) {
            boletaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}