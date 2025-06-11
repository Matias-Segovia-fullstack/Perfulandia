package com.Perfulandia.msvc.producto.services;

import com.Perfulandia.msvc.producto.exceptions.ProductoException;
import com.Perfulandia.msvc.producto.models.entities.Producto;
import com.Perfulandia.msvc.producto.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Producto>  findAll() {
        return this.productoRepository.findAll();
    }

    @Override
    public Producto findById(Long id) {
        return this.productoRepository.findById(id).orElseThrow(
                () ->new ProductoException("El producto con id "+id+" no existe")
        );
    }

    @Override
    public Producto save(Producto producto) {
        Producto productoEntity = new Producto();
        productoEntity.setIdProducto(producto.getIdProducto());
        return this.productoRepository.save(producto);
    }

    @Override
    public Producto actualizar(Long id, Producto nuevoProducto){
        Producto productoTemp = productoRepository.findById(id).orElseThrow(
                () ->new ProductoException("El producto con id "+id+" no existe"));
        productoTemp.setNombreProducto(nuevoProducto.getNombreProducto());
        productoTemp.setPrecioProducto(nuevoProducto.getPrecioProducto());

        return this.productoRepository.save(productoTemp);
    }

    @Override
    public void borrar(Long id) {
        Producto productoTemp = productoRepository.findById(id).orElseThrow(
                () -> new ProductoException("El producto con id " + id + " no existe"));
        productoRepository.deleteById(id);
    }
}
