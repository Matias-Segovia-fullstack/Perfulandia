package com.Perfulandia.msvc.detalleBoleta.services;

import com.Perfulandia.msvc.detalleBoleta.clients.ProductoClient;
import com.Perfulandia.msvc.detalleBoleta.dto.ProductoDTO;
import com.Perfulandia.msvc.detalleBoleta.models.entities.DetalleBoleta;
import com.Perfulandia.msvc.detalleBoleta.repositories.DetalleBoletaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleBoletaServiceImpl implements DetalleBoletaService {

    @Autowired
    private DetalleBoletaRepository repository;

    @Autowired
    private ProductoClient productoClient;

    @Override
    public DetalleBoleta guardar(DetalleBoleta detalle) {
        ProductoDTO producto = productoClient.obtenerProducto(detalle.getProductoId());

        if (producto == null) {
            throw new RuntimeException("Producto no encontrado con ID: " + detalle.getProductoId());
        }

        detalle.setPrecioUnitario(producto.getPrecioProducto().doubleValue());
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
