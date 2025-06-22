package com.Perfulandia.msvc.producto.services;

import com.Perfulandia.msvc.producto.exceptions.ProductoException;
import com.Perfulandia.msvc.producto.models.entities.Producto;
import com.Perfulandia.msvc.producto.repositories.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoServiceImpl productoService;

    private Producto producto;

    @BeforeEach
    void setUp() {
        producto = new Producto();
        producto.setIdProducto(1L);
        producto.setNombreProducto("Perfume A");
        producto.setPrecioProducto(5000);
    }

    @Test
    @DisplayName("Guardar producto exitosamente")
    void shouldSaveProducto() {
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        Producto resultado = productoService.save(producto);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombreProducto()).isEqualTo("Perfume A");
        assertThat(resultado.getPrecioProducto()).isEqualTo(5000);

        verify(productoRepository).save(any(Producto.class));
    }

    @Test
    @DisplayName("Actualizar producto existente correctamente")
    void shouldUpdateProducto() {
        Producto actualizado = new Producto();
        actualizado.setNombreProducto("Nuevo Perfume");
        actualizado.setPrecioProducto(8000);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(productoRepository.save(any(Producto.class))).thenReturn(actualizado);

        Producto resultado = productoService.actualizar(1L, actualizado);

        assertThat(resultado.getNombreProducto()).isEqualTo("Nuevo Perfume");
        assertThat(resultado.getPrecioProducto()).isEqualTo(8000);

        verify(productoRepository).findById(1L);
        verify(productoRepository).save(any(Producto.class));
    }

    @Test
    @DisplayName("Error al actualizar producto inexistente")
    void shouldThrowExceptionWhenUpdatingNonexistentProducto() {
        Producto nuevo = new Producto();
        nuevo.setNombreProducto("X");
        nuevo.setPrecioProducto(9999);

        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productoService.actualizar(99L, nuevo))
                .isInstanceOf(ProductoException.class)
                .hasMessageContaining("no existe");

        verify(productoRepository).findById(99L);
        verify(productoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Eliminar producto correctamente")
    void shouldDeleteProducto() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        productoService.borrar(1L);

        verify(productoRepository).findById(1L);
        verify(productoRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Error al eliminar producto inexistente")
    void shouldThrowExceptionWhenDeletingNonexistentProducto() {
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productoService.borrar(99L))
                .isInstanceOf(ProductoException.class)
                .hasMessageContaining("no existe");

        verify(productoRepository).findById(99L);
        verify(productoRepository, never()).deleteById(any());
    }
}

