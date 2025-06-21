package com.Perfulandia.msvc.detalleBoleta.services;

import com.Perfulandia.msvc.detalleBoleta.clients.BoletaClient;
import com.Perfulandia.msvc.detalleBoleta.clients.InventarioClient;
import com.Perfulandia.msvc.detalleBoleta.clients.ProductoClient;
import com.Perfulandia.msvc.detalleBoleta.dto.BoletaDTO;
import com.Perfulandia.msvc.detalleBoleta.dto.InventarioDTO;
import com.Perfulandia.msvc.detalleBoleta.dto.ProductoDTO;
import com.Perfulandia.msvc.detalleBoleta.exceptions.DetalleBoletaException;
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

    @Autowired
    private BoletaClient boletaClient;

    @Autowired
    private InventarioClient inventarioClient;

    @Override
    public DetalleBoleta guardar(DetalleBoleta detalle) {
        // Validar que el producto exista
        ProductoDTO producto = productoClient.obtenerProducto(detalle.getProductoId());
        if (producto == null) {
            throw new DetalleBoletaException("Producto con id " + detalle.getProductoId() + " no encontrado");
        }

        // Obtener la boleta para identificar la sucursal
        BoletaDTO boleta = boletaClient.obtenerBoleta(detalle.getIdBoleta());
        if (boleta == null) {
            throw new DetalleBoletaException("Boleta con id " + detalle.getIdBoleta() + " no encontrada");
        }

        // Obtener el inventario de ese producto en esa sucursal
        InventarioDTO inventario = inventarioClient.findByProductAndSucursal(
                boleta.getIdSucursal(), detalle.getProductoId());

        if (inventario == null) {
            throw new DetalleBoletaException("No hay inventario para el producto " + detalle.getProductoId() +
                    " en la sucursal " + boleta.getIdSucursal());
        }

        if (inventario.getStockActual() < detalle.getCantidad()) {
            throw new DetalleBoletaException("Stock insuficiente para el producto " + detalle.getProductoId());
        }

        // Actualizar stock
        int nuevoStock = inventario.getStockActual() - detalle.getCantidad();
        inventarioClient.actualizarStock(boleta.getIdSucursal(), detalle.getProductoId(), nuevoStock);

        // Establecer precio y guardar detalle
        detalle.setPrecioUnitario(producto.getPrecioProducto().doubleValue());


        return repository.save(detalle);
    }

    @Override
    public List<DetalleBoleta> listarPorBoleta(Long idBoleta) {
        return repository.findByIdBoleta(idBoleta);
    }

    @Override
    public Optional<DetalleBoleta> obtener(Long id) {
        return repository.findById(id);
    }

    @Override
    public void borrar(Long id) {
        DetalleBoleta detalle = repository.findById(id).orElseThrow(
                () -> new DetalleBoletaException("El detalle con id " + id + " no existe"));
        repository.deleteById(id);
    }

    @Override
    public DetalleBoleta actualizar(Long id, DetalleBoleta nuevoDetalle) {
        DetalleBoleta existente = repository.findById(id)
                .orElseThrow(() -> new DetalleBoletaException("No se encontró el detalle con ID: " + id));
        existente.setIdBoleta(nuevoDetalle.getIdBoleta());
        existente.setProductoId(nuevoDetalle.getProductoId());
        existente.setCantidad(nuevoDetalle.getCantidad());
        existente.setPrecioUnitario(nuevoDetalle.getPrecioUnitario());
        return repository.save(existente);
    }
}




