package com.Perfulandia.msvc.sucursal.services;

import com.Perfulandia.msvc.sucursal.exceptions.SucursalException;
import com.Perfulandia.msvc.sucursal.models.entities.Sucursal;
import com.Perfulandia.msvc.sucursal.repositories.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SucursalServiceImpl implements SucursalService{

    @Autowired
    private SucursalRepository sucursalRepository;

    @Override
    public List<Sucursal> findAll() {
        return this.sucursalRepository.findAll();
    }

    @Override
    public Sucursal findById(Long id) {
        return this.sucursalRepository.findById(id).orElseThrow(
                () ->new SucursalException("la sucursal con id "+id+" no existe"));
    }

    @Override
    public Sucursal save(Sucursal sucursal) {
        return this.sucursalRepository.save(sucursal);
    }

    @Override
    public Sucursal actualizar(Long id, Sucursal nuevaSucursal) {
        Sucursal sucursalTemp = sucursalRepository.findById(id).orElseThrow(
                () ->new SucursalException("La sucursal con id "+id+" no existe"));
        sucursalTemp.setNombreSucursal(nuevaSucursal.getNombreSucursal());
        sucursalTemp.setDireccion(nuevaSucursal.getDireccion());

        return this.sucursalRepository.save(sucursalTemp);
    }

    @Override
    public void borrar(Long id) {
        Sucursal sucursalTemp = sucursalRepository.findById(id).orElseThrow(
                () ->new SucursalException("La sucursal con id "+id+" no existe"));
        sucursalRepository.deleteById(id);
    }
}
