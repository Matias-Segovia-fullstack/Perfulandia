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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DetalleBoletaServiceTest {

    @Mock
    private DetalleBoletaRepository repository;

    @Mock
    private ProductoClient productoClient;

    @Mock
    private BoletaClient boletaClient;

    @Mock
    private InventarioClient inventarioClient;

    @InjectMocks
    private DetalleBoletaServiceImpl service;

    private DetalleBoleta detalle;

    @BeforeEach
    void setUp() {
        detalle = new DetalleBoleta();
        detalle.setId(1L);
        detalle.setIdBoleta(100L);
        detalle.setProductoId(200L);
        detalle.setCantidad(3);
        detalle.setPrecioUnitario(1500.0);
    }

    @Test
    @DisplayName("Guardar detalle exitosamente con stock suficiente")
    void shouldSaveDetalleBoletaWhenStockIsSufficient() {
        ProductoDTO producto = new ProductoDTO();
        producto.setIdProducto(200L);
        producto.setPrecioProducto(15000);

        BoletaDTO boleta = new BoletaDTO();
        boleta.setId(100L);
        boleta.setIdSucursal(1L);

        InventarioDTO inventario = new InventarioDTO();
        inventario.setId(1L);
        inventario.setStockActual(10);

        when(productoClient.obtenerProducto(200L)).thenReturn(producto);
        when(boletaClient.obtenerBoleta(100L)).thenReturn(boleta);
        when(inventarioClient.findByProductAndSucursal(1L, 200L)).thenReturn(inventario);
        when(repository.save(any(DetalleBoleta.class))).thenReturn(detalle);

        DetalleBoleta resultado = service.guardar(detalle);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getCantidad()).isEqualTo(3);
        assertThat(resultado.getPrecioUnitario()).isEqualTo(15000);

        verify(productoClient).obtenerProducto(200L);
        verify(boletaClient).obtenerBoleta(100L);
        verify(inventarioClient).findByProductAndSucursal(1L, 200L);
        verify(inventarioClient).actualizarStock(1L, 200L, 7);
        verify(repository).save(any(DetalleBoleta.class));
    }

    @Test
    @DisplayName("Lanza excepción si no hay stock suficiente")
    void shouldThrowExceptionWhenStockIsInsufficient() {
        ProductoDTO producto = new ProductoDTO();
        producto.setIdProducto(200L);
        producto.setPrecioProducto(15000);

        BoletaDTO boleta = new BoletaDTO();
        boleta.setId(100L);
        boleta.setIdSucursal(1L);

        InventarioDTO inventario = new InventarioDTO();
        inventario.setStockActual(2);

        when(productoClient.obtenerProducto(200L)).thenReturn(producto);
        when(boletaClient.obtenerBoleta(100L)).thenReturn(boleta);
        when(inventarioClient.findByProductAndSucursal(1L, 200L)).thenReturn(inventario);

        assertThatThrownBy(() -> service.guardar(detalle))
                .isInstanceOf(DetalleBoletaException.class)
                .hasMessageContaining("Stock insuficiente");

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Lanza excepción si el detalle no existe al eliminar")
    void shouldThrowExceptionWhenDeletingNonexistentDetalle() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.borrar(99L))
                .isInstanceOf(DetalleBoletaException.class)
                .hasMessageContaining("no existe");

        verify(repository, times(1)).findById(99L);
        verify(repository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Actualiza detalle existente correctamente")
    void shouldUpdateExistingDetalle() {
        DetalleBoleta actualizado = new DetalleBoleta();
        actualizado.setIdBoleta(101L);
        actualizado.setProductoId(201L);
        actualizado.setCantidad(5);
        actualizado.setPrecioUnitario(2000.0);

        when(repository.findById(1L)).thenReturn(Optional.of(detalle));
        when(repository.save(any(DetalleBoleta.class))).thenReturn(actualizado);

        DetalleBoleta resultado = service.actualizar(1L, actualizado);

        assertThat(resultado.getIdBoleta()).isEqualTo(101L);
        assertThat(resultado.getProductoId()).isEqualTo(201L);
        assertThat(resultado.getCantidad()).isEqualTo(5);
        assertThat(resultado.getPrecioUnitario()).isEqualTo(2000.0);

        verify(repository).findById(1L);
        verify(repository).save(any(DetalleBoleta.class));
    }

    @Test
    @DisplayName("Devuelve lista de detalles por ID de boleta")
    void shouldReturnDetallesByIdBoleta() {
        List<DetalleBoleta> detalles = List.of(detalle);
        when(repository.findByIdBoleta(100L)).thenReturn(detalles);

        List<DetalleBoleta> resultado = service.listarPorBoleta(100L);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.getFirst().getIdBoleta()).isEqualTo(100L);
        verify(repository).findByIdBoleta(100L);
    }

    @Test
    @DisplayName("Lanza excepción si el detalle no existe al buscar por ID")
    void shouldThrowExceptionWhenFindingNonexistentDetalle() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.obtener(999L).orElseThrow(
                () -> new DetalleBoletaException("Detalle con id 999 no existe")))
                .isInstanceOf(DetalleBoletaException.class)
                .hasMessageContaining("no existe");

        verify(repository).findById(999L);
    }


    @Test
    @DisplayName("Elimina detalle correctamente si existe")
    void shouldDeleteExistingDetalle() {
        when(repository.findById(1L)).thenReturn(Optional.of(detalle));
        doNothing().when(repository).deleteById(1L);

        service.borrar(1L);

        verify(repository).findById(1L);
        verify(repository).deleteById(1L);
    }

}

