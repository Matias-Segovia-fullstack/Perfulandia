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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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

    private Producto productoTest;

    @BeforeEach
    void setUp() {
        productoTest = new Producto(
                1L, "Perfume A", 5000
        );

    }

    @Test
    @DisplayName("Crear producto")
    public void shouldCreateProducto() {
        when(productoRepository.save(any(Producto.class))).thenReturn(productoTest);

        Producto result = productoService.save(productoTest);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(this.productoTest);

        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    @DisplayName("Error al crear producto")
    public void shouldThrowExceptionWhenSaveFails() {
        when(productoRepository.save(any(Producto.class)))
                .thenThrow(new ProductoException("Error al guardar en la base de datos"));

        assertThatThrownBy(() -> productoService.save(productoTest))
                .isInstanceOf(ProductoException.class)
                .hasMessageContaining("guardar en la base de datos");

        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    @DisplayName("Listar productos")
    public void shouldListProductos() {
        List<Producto> productos = Arrays.asList(this.productoTest);

        when(productoRepository.findAll()).thenReturn(productos);

        List<Producto> result = this.productoService.findAll();

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result).contains(this.productoTest);

        verify(productoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Error al listar productos")
    public void shouldThrowExceptionWhenListProductosFails() {
        when(productoRepository.findAll())
                .thenThrow(new ProductoException("Error al listar productos"));

        assertThatThrownBy(() -> productoService.findAll())
                .isInstanceOf(ProductoException.class)
                .hasMessageContaining("Error al listar");

        verify(productoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Listar productos cuando no hay ninguno")
    public void shouldReturnEmptyListWhenNoProductosExist() {
        when(productoRepository.findAll()).thenReturn(Collections.emptyList());

        List<Producto> result = productoService.findAll();

        assertThat(result).isNotNull();
        assertThat(result).isEmpty();

        verify(productoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Buscar producto por id")
    public void shouldBuscarProductoPorId() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(this.productoTest));

        Producto result = productoService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(productoTest);

        verify(productoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Buscar producto por ID no encontrado")
    public void shouldThrowExceptionWhenProductoNotFound() {
        when(productoRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productoService.findById(999L))
                .isInstanceOf(ProductoException.class)
                .hasMessageContaining("no existe");

        verify(productoRepository).findById(999L);
    }

    @Test
    @DisplayName("Error técnico al buscar producto por ID")
    public void shouldThrowExceptionWhenFindByIdFails() {
        when(productoRepository.findById(anyLong()))
                .thenThrow(new ProductoException("Error técnico al buscar producto"));

        assertThatThrownBy(() -> productoService.findById(1L))
                .isInstanceOf(ProductoException.class)
                .hasMessageContaining("Error técnico");

        verify(productoRepository).findById(1L);
    }

    @Test
    @DisplayName("Actualizar producto")
    public void shouldActualizarProducto() {
        Producto nuevoProducto = new Producto();
        nuevoProducto.setNombreProducto("Nuevo Producto");
        nuevoProducto.setPrecioProducto(1000);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(this.productoTest));
        when(productoRepository.save(any(Producto.class))).thenReturn(nuevoProducto);

        Producto result = productoService.actualizar(1L, nuevoProducto);

        assertThat(result.getNombreProducto()).isEqualTo(nuevoProducto.getNombreProducto());
        assertThat(result.getPrecioProducto()).isEqualTo(nuevoProducto.getPrecioProducto());

        verify(productoRepository, times(1)).findById(1L);
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    @DisplayName("Error al actualizar producto inexistente")
    public void shouldThrowExceptionWhenUpdatingNonexistentProducto() {
        when(productoRepository.findById(999L)).thenReturn(Optional.empty());

        Producto productoActualizado = new Producto(999L, "nuevo producto", 2000);

        assertThatThrownBy(() -> productoService.actualizar(999L, productoActualizado))
                .isInstanceOf(ProductoException.class)
                .hasMessageContaining("no existe");

        verify(productoRepository).findById(999L);
        verify(productoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Error técnico al actualizar producto")
    public void shouldThrowExceptionWhenSaveFailsDuringUpdate() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoTest));
        when(productoRepository.save(any(Producto.class)))
                .thenThrow(new ProductoException("Error técnico al guardar"));

        Producto productoActualizado = new Producto(1L, "Nuevo Nombre", 1500);

        assertThatThrownBy(() -> productoService.actualizar(1L, productoActualizado))
                .isInstanceOf(ProductoException.class)
                .hasMessageContaining("Error técnico");

        verify(productoRepository).findById(1L);
        verify(productoRepository).save(any(Producto.class));
    }

    @Test
    @DisplayName("Borrar producto")
    public void shouldBorrarProducto() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoTest));

        productoService.borrar(1L);

        verify(productoRepository).findById(1L);
        verify(productoRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Error al eliminar producto inexistente")
    public void shouldThrowExceptionWhenDeletingNonexistentProducto() {
        when(productoRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productoService.borrar(999L))
                .isInstanceOf(ProductoException.class)
                .hasMessageContaining("no existe");

        verify(productoRepository).findById(999L);
        verify(productoRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Error técnico al eliminar producto")
    public void shouldThrowExceptionWhenDeleteFails() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoTest));
        doThrow(new ProductoException("Error técnico al eliminar")).when(productoRepository).deleteById(1L);

        assertThatThrownBy(() -> productoService.borrar(1L))
                .isInstanceOf(ProductoException.class)
                .hasMessageContaining("Error técnico");

        verify(productoRepository).findById(1L);
        verify(productoRepository).deleteById(1L);
    }
}