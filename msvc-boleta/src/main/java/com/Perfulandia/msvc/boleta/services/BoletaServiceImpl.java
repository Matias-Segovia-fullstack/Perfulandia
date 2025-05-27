package com.Perfulandia.msvc.boleta.services;

import com.Perfulandia.msvc.boleta.clients.UsuarioClient;
import com.Perfulandia.msvc.boleta.clients.SucursalClient;
import com.Perfulandia.msvc.boleta.dto.UsuarioDTO;
import com.Perfulandia.msvc.boleta.dto.SucursalDTO;
import com.Perfulandia.msvc.boleta.exceptions.BoletaException;
import com.Perfulandia.msvc.boleta.models.entities.Boleta;
import com.Perfulandia.msvc.boleta.repositories.BoletaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BoletaServiceImpl implements BoletaService {

    @Autowired
    private BoletaRepository boletaRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private SucursalClient sucursalClient;

    @Override
    public List<Boleta> findAll() {
        return boletaRepository.findAll();
    }

    @Override
    public Boleta findById(Long id) {
        return boletaRepository.findById(id)
                .orElseThrow(() -> new BoletaException("La boleta con id " + id + " no existe"));
    }

    @Override
    public Boleta save(Boleta boleta) {
        // Validar que el cliente exista
        UsuarioDTO usuario = usuarioClient.obtenerUsuario(boleta.getIdUsuario());
        if (usuario == null) {
            throw new BoletaException("El cliente con id " + boleta.getIdUsuario() + " no existe");
        }

        // Validar que la sucursal exista
        SucursalDTO sucursal = sucursalClient.obtenerSucursal(boleta.getIdSucursal());
        if (sucursal == null) {
            throw new BoletaException("La sucursal con id " + boleta.getIdSucursal() + " no existe");
        }

        boleta.setFechaCreacion(LocalDateTime.now());
        return boletaRepository.save(boleta);
    }
}



