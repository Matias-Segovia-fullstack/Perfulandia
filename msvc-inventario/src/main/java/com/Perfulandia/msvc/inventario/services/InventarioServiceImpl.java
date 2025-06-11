package com.Perfulandia.msvc.inventario.services;

import com.Perfulandia.msvc.inventario.exceptions.InventarioException;
import com.Perfulandia.msvc.inventario.models.entities.Inventario;
import com.Perfulandia.msvc.inventario.repositories.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class InventarioServiceImpl implements InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Override
    public List<Inventario> findAll() {return this.inventarioRepository.findAll();}

    @Override
    public Inventario findById(Long id) {
        return this.inventarioRepository.findById(id).orElseThrow(
                () ->new InventarioException("El inventario con id "+id+" no existe")
        );
    }

    @Override
    public Inventario save(Inventario inventario) {
        return this.inventarioRepository.save(inventario);
    }

    @Override
    public List<Inventario> findByIdSucursal(Long idSucursal) {
        List<Inventario> inventarioSucursal = inventarioRepository.findByIdSucursal(idSucursal);
        if (inventarioSucursal.isEmpty()) {
            throw new InventarioException("No hay inventario registrado para la sucursal con ID " + idSucursal);
        }
        return inventarioSucursal;
    }

    @Override
    public List<Inventario> findByIdProducto(Long idProducto) {
        List<Inventario> inventarioProducto = inventarioRepository.findByIdProducto(idProducto);
        if (inventarioProducto.isEmpty()) {
            throw new InventarioException("No hay inventario registrado para el producto con ID " + idProducto);
        }

        return inventarioProducto;
    }

    @Override
    public Inventario findByProductAndSucursal(Long idSucursal, Long idProducto){
        return this.inventarioRepository.findByIdProductAndSucursal(idSucursal, idProducto).orElseThrow(
                () ->new InventarioException("el producto "+idProducto+" no existe en la sucursal "+idSucursal));
    }

    @Override
    public Inventario actualizarStock(Long idSucursal, Long idProducto, int nuevoStock) {
        Inventario inventario = inventarioRepository.findByIdProductAndSucursal(idSucursal, idProducto).orElseThrow(
                () ->new InventarioException("el producto "+idProducto+" no existe en la sucursal "+idSucursal));

        inventario.setStockActual(nuevoStock);
        return this.inventarioRepository.save(inventario);
    }
}
