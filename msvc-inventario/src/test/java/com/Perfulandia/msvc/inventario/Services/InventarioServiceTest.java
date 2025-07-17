package com.Perfulandia.msvc.inventario.Services;

import com.Perfulandia.msvc.inventario.clients.ProductoClient;
import com.Perfulandia.msvc.inventario.clients.SucursalClient;
import com.Perfulandia.msvc.inventario.dto.ProductoDTO;
import com.Perfulandia.msvc.inventario.dto.SucursalDTO;
import com.Perfulandia.msvc.inventario.exceptions.InventarioException;
import com.Perfulandia.msvc.inventario.models.entities.Inventario;
import com.Perfulandia.msvc.inventario.repositories.InventarioRepository;
import com.Perfulandia.msvc.inventario.services.InventarioService;
import com.Perfulandia.msvc.inventario.services.InventarioServiceImpl;
import com.Perfulandia.msvc.producto.models.entities.Producto;
import com.Perfulandia.msvc.sucursal.models.entities.Sucursal;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @Mock
    private ProductoClient productoClient;

    @Mock
    private SucursalClient sucursalClient;

    @InjectMocks
    private InventarioServiceImpl inventarioService;

    private ProductoDTO productoTest;
    private SucursalDTO sucursalTest;
    private Inventario inventarioTest;


    @BeforeEach
    public void setUp() {
        productoTest = new ProductoDTO(
                1L, "Perfume Hombre", 49990
        );
        sucursalTest = new SucursalDTO(
                1L, "Sucursal Valparaiso", "Plaza Victoria"
        );
        inventarioTest = new Inventario(
                1L, 1L, 1L, 13
        );

    }

    @Test
    @DisplayName("Debe Crear Inventario")
    public void shouldCreateInventario() {
        when(productoClient.obtenerProducto(1L)).thenReturn(this.productoTest);
        when(sucursalClient.obtenerSucursal(1L)).thenReturn((this.sucursalTest));
        when(inventarioRepository.save(any(Inventario.class))).thenReturn(this.inventarioTest);

        Inventario result = inventarioService.save(this.inventarioTest);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(this.inventarioTest);

        verify(productoClient, times(1)).obtenerProducto(1L);
        verify(sucursalClient, times(1)).obtenerSucursal(1L);
        verify(inventarioRepository, times(1)).save(any(Inventario.class));
    }

    @Test
    @DisplayName("Error al crear inventario")
    public void shouldThrowExceptionWhenSaveInventarioFails() {
        when(productoClient.obtenerProducto(1L)).thenReturn(this.productoTest);
        when(sucursalClient.obtenerSucursal(1L)).thenReturn(this.sucursalTest);
        when(inventarioRepository.save(any(Inventario.class)))
                .thenThrow(new InventarioException("Error al guardar inventario"));

        assertThatThrownBy(() -> inventarioService.save(this.inventarioTest))
                .isInstanceOf(InventarioException.class)
                .hasMessageContaining("Error al guardar inventario");

        verify(productoClient, times(1)).obtenerProducto(1L);
        verify(sucursalClient, times(1)).obtenerSucursal(1L);
        verify(inventarioRepository, times(1)).save(any(Inventario.class));
    }

    @Test
    @DisplayName("Listar todos los inventarios")
    public void shouldListarInventarios() {
        List<Inventario> inventarios = Arrays.asList(this.inventarioTest);

        when(inventarioRepository.findAll()).thenReturn(inventarios);

        List<Inventario> result = this.inventarioService.findAll();

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result).contains(this.inventarioTest);

        verify(inventarioRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Error al listar inventarios")
    public void shouldThrowExceptionWhenListInventariosFails() {
        when(inventarioRepository.findAll())
                .thenThrow(new InventarioException("Error al listar inventarios"));

        assertThatThrownBy(() -> inventarioService.findAll())
                .isInstanceOf(InventarioException.class)
                .hasMessageContaining("Error al listar");

        verify(inventarioRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Listar inventarios cuando no hay ninguno")
    public void shouldReturnEmptyListWhenNoInventariosExist() {
        when(inventarioRepository.findAll()).thenReturn(Collections.emptyList());

        List<Inventario> result = inventarioService.findAll();

        assertThat(result).isNotNull();
        assertThat(result).isEmpty();

        verify(inventarioRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Buscar por id")
    public void shouldBuscarInventarioPorId(){
        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(this.inventarioTest));

        Inventario result = inventarioService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(inventarioTest);

        verify(inventarioRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Buscar inventario por ID no encontrado")
    public void shouldThrowExceptionWhenInventarioNotFound() {
        when(inventarioRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> inventarioService.findById(999L))
                .isInstanceOf(InventarioException.class)
                .hasMessageContaining("no existe");

        verify(inventarioRepository).findById(999L);
    }

    @Test
    @DisplayName("Error técnico al buscar inventario por ID")
    public void shouldThrowExceptionWhenFindByIdFails() {
        when(inventarioRepository.findById(anyLong()))
                .thenThrow(new InventarioException("Error técnico al buscar inventario"));

        assertThatThrownBy(() -> inventarioService.findById(1L))
                .isInstanceOf(InventarioException.class)
                .hasMessageContaining("Error técnico");

        verify(inventarioRepository).findById(1L);
    }

    @Test
    @DisplayName("Buscar por id sucursal")
    public void shouldBuscarPorIdSucursal(){
        List<Inventario> inventarios = Arrays.asList(this.inventarioTest);

        when(inventarioRepository.findByIdSucursal(1L)).thenReturn(inventarios);

        List<Inventario> result = inventarioService.findByIdSucursal(1L);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result).contains(this.inventarioTest);

        verify(inventarioRepository, times(1)).findByIdSucursal(1L);
    }

    @Test
    @DisplayName("Error al buscar por id de sucursal inexistente")
    public void shouldThrowExceptionWhenIdSucursalNotFound() {
        when(inventarioRepository.findByIdSucursal(999L)).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> inventarioService.findByIdSucursal(999L))
                .isInstanceOf(InventarioException.class)
                .hasMessageContaining("No hay inventario registrado para la sucursal");

        verify(inventarioRepository, times(1)).findByIdSucursal(999L);
    }

    @Test
    @DisplayName("Error técnico al buscar inventario por ID sucursal")
    public void shouldThrowExceptionWhenFindByIdSucursalFails() {
        when(inventarioRepository.findByIdSucursal(anyLong()))
                .thenThrow(new InventarioException("Error técnico al buscar inventario por sucursal"));

        assertThatThrownBy(() -> inventarioService.findByIdSucursal(1L))
                .isInstanceOf(InventarioException.class)
                .hasMessageContaining("Error técnico");

        verify(inventarioRepository).findByIdSucursal(1L);
    }

    @Test
    @DisplayName("Buscar por id producto")
    public void shouldBuscarPorIdProducto(){
        List<Inventario> inventarios = Arrays.asList(this.inventarioTest);

        when(inventarioRepository.findByIdProducto(1L)).thenReturn(inventarios);

        List<Inventario> result = inventarioService.findByIdProducto(1L);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result).contains(this.inventarioTest);

        verify(inventarioRepository, times(1)).findByIdProducto(1L);
    }

    @Test
    @DisplayName("Error al buscar por id de producto inexistente")
    public void shouldThrowExceptionWhenIdProductoNotFound() {
        when(inventarioRepository.findByIdProducto(999L)).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> inventarioService.findByIdProducto(999L))
                .isInstanceOf(InventarioException.class)
                .hasMessageContaining("No hay inventario registrado para el producto");

        verify(inventarioRepository, times(1)).findByIdProducto(999L);
    }

    @Test
    @DisplayName("Error técnico al buscar inventario por ID producto")
    public void shouldThrowExceptionWhenFindByIdProductoFails() {
        when(inventarioRepository.findByIdProducto(anyLong()))
                .thenThrow(new InventarioException("Error técnico al buscar inventario por producto"));

        assertThatThrownBy(() -> inventarioService.findByIdProducto(1L))
                .isInstanceOf(InventarioException.class)
                .hasMessageContaining("Error técnico");

        verify(inventarioRepository).findByIdProducto(1L);
    }

    @Test
    @DisplayName("Buscar inventario por producto y sucursal")
    public void shouldFindInventarioByProductoAndSucursal() {
        when(inventarioRepository.findByIdProductoAndIdSucursal(1L, 1L))
                .thenReturn(Collections.singletonList(this.inventarioTest));

        Inventario result = inventarioService.findByProductoAndSucursal(1L, 1L);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(this.inventarioTest);

        verify(inventarioRepository, times(1)).findByIdProductoAndIdSucursal(1L, 1L);
    }

    @Test
    @DisplayName("Error técnico al buscar inventario por producto y sucursal")
    public void shouldThrowExceptionWhenDatabaseFails() {
        when(inventarioRepository.findByIdProductoAndIdSucursal(anyLong(), anyLong()))
                .thenThrow(new InventarioException("Error técnico al buscar inventario"));

        assertThatThrownBy(() -> inventarioService.findByProductoAndSucursal(1L, 1L))
                .isInstanceOf(InventarioException.class)
                .hasMessageContaining("Error técnico");

        verify(inventarioRepository).findByIdProductoAndIdSucursal(1L, 1L);
    }


    @Test
    @DisplayName("Actualizar stock de inventario")
    public void shouldActualizarStock() {
        when(inventarioRepository.findByIdProductoAndIdSucursal(1L, 1L))
                .thenReturn(Collections.singletonList(this.inventarioTest));
        when(inventarioRepository.save(any(Inventario.class))).thenReturn(this.inventarioTest);

        Integer nuevoStock = 25;

        Inventario result = inventarioService.actualizarStock(1L, 1L, nuevoStock);

        assertThat(result).isNotNull();
        assertThat(result.getStockActual()).isEqualTo(nuevoStock);

        verify(inventarioRepository, times(1))
                .findByIdProductoAndIdSucursal(1L, 1L);
        verify(inventarioRepository, times(1)).save(any(Inventario.class));
    }

    @Test
    @DisplayName("Actualizar stock lanza excepción técnica")
    public void shouldThrowTechnicalErrorWhenActualizarStock() {
        when(inventarioRepository.findByIdProductoAndIdSucursal(1L, 1L))
                .thenThrow(new RuntimeException("Error técnico DB"));

        assertThrows(RuntimeException.class, () -> {
            inventarioService.actualizarStock(1L, 1L, 25);
        });

        verify(inventarioRepository).findByIdProductoAndIdSucursal(1L, 1L);
    }

    @Test
    @DisplayName("Borrar inventario")
    public void shouldBorrarInventario(){
        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(inventarioTest));

        inventarioService.borrar(1L);

        verify(inventarioRepository).findById(1L);
        verify(inventarioRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Error al eliminar inventario inexistente")
    public void shouldThrowExceptionWhenDeletingNonexistentInventario() {
        when(inventarioRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> inventarioService.borrar(999L))
                .isInstanceOf(InventarioException.class)
                .hasMessageContaining("no existe");

        verify(inventarioRepository).findById(999L);
        verify(inventarioRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Borrar inventario lanza excepción técnica")
    public void shouldThrowTechnicalErrorWhenBorrarInventario() {
        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(inventarioTest));
        doThrow(new InventarioException("Error técnico al eliminar")).when(inventarioRepository).deleteById(1L);

        assertThatThrownBy(() -> inventarioService.borrar(1L))
                .isInstanceOf(InventarioException.class)
                .hasMessageContaining("Error técnico");


        verify(inventarioRepository).findById(1L);
        verify(inventarioRepository).deleteById(1L);
    }

}


