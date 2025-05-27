package com.Perfulandia.msvc.inventario.services;

import com.Perfulandia.msvc.inventario.exceptions.InventarioException;
import com.Perfulandia.msvc.inventario.models.entities.Inventario;
import com.Perfulandia.msvc.inventario.repositories.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventarioServiceImpl implements InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Override
    public Optional<Inventario> findByIdProducto(Long idProducto) {
        return this.inventarioRepository.findByProduct_Id(idProducto);
    }

    @Override
    public Inventario actualizarStock(Long idProducto, int cantidad) {
        Inventario inventario = inventarioRepository.findByProduct_Id(idProducto).orElseThrow(
                () -> new InventarioException("Inventario no encontrado"));

        inventario.setStockActual(cantidad);
        return this.inventarioRepository.save(inventario);
    }

    @Override
    public Inventario save(Inventario inventario) {
        return this.inventarioRepository.save(inventario);
    }
}
