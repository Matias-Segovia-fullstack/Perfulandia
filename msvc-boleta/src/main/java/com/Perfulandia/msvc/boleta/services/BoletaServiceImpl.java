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
        UsuarioDTO usuarioDTO = usuarioClient.obtenerUsuario(boleta.getIdUsuario());
        if (usuarioDTO == null) {
            throw new BoletaException("El cliente con id " + boleta.getIdUsuario() + " no existe");
        }

        // Validar que la sucursal exista
        SucursalDTO sucursalDTO = sucursalClient.obtenerSucursal(boleta.getIdSucursal());
        if (sucursalDTO == null) {
            throw new BoletaException("La sucursal con id " + boleta.getIdSucursal() + " no existe");
        }

        boleta.setFechaCreacion(LocalDateTime.now());
        return boletaRepository.save(boleta);
    }

    @Override
    public List<Boleta> findByIdUsuario(Long idUsuario) {
        List<Boleta> boletaUsuario = boletaRepository.findByIdUsuario(idUsuario);
        if(boletaUsuario.isEmpty()){
            throw new BoletaException("No hay boletas registradas del usuario con id "+idUsuario);
        }
        return boletaUsuario;
    }

    @Override
    public List<Boleta> findByIdSucursal(Long idSucursal) {
        List<Boleta> boletaSucursal = boletaRepository.findByIdSucursal(idSucursal);
        if(boletaSucursal.isEmpty()){
           throw new BoletaException("No hay boletas registradas en la sucursal con id "+idSucursal);
        }
        return boletaSucursal;
    }

    @Override
    public Boleta actualizar(Long id, Boleta nuevaBoleta) {
        Boleta boletaTemp = boletaRepository.findById(id).orElseThrow(
                ()-> new BoletaException("No existe la boleta con id "+id));
        boletaTemp.setIdUsuario(nuevaBoleta.getIdUsuario());
        boletaTemp.setIdSucursal(nuevaBoleta.getIdSucursal());
        boletaTemp.setTotal(nuevaBoleta.getTotal());

        return this.boletaRepository.save(boletaTemp);
    }

    @Override
    public void borrar(Long id) {
        Boleta boletaTemp = boletaRepository.findById(id).orElseThrow(
                ()-> new BoletaException("No existe la boleta con id "+id));
        boletaRepository.deleteById(id);
    }
}



