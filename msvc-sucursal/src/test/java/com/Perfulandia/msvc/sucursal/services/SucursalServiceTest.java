package com.Perfulandia.msvc.sucursal.services;

import com.Perfulandia.msvc.sucursal.exceptions.SucursalException;
import com.Perfulandia.msvc.sucursal.models.entities.Sucursal;
import com.Perfulandia.msvc.sucursal.repositories.SucursalRepository;
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
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SucursalServiceTest {

    @Mock
    private SucursalRepository sucursalRepository;

    @InjectMocks
    private SucursalServiceImpl sucursalService;

    private Sucursal sucursalTest;

    @BeforeEach
    public void setUp(){
        sucursalTest = new Sucursal(
                1L, "Sucursal Valparaiso", "Plaza Victoria"
        );
    }

    @Test
    @DisplayName("Crear sucursal")
    public void shouldCreateSucursal(){
        when(sucursalRepository.save(any(Sucursal.class))).thenReturn(sucursalTest);

        Sucursal result = sucursalService.save(sucursalTest);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(this.sucursalTest);

        verify(sucursalRepository, times(1)).save(any(Sucursal.class));
    }

    @Test
    @DisplayName("Error al crear sucursal")
    public void shouldThrowExceptionWhenSaveFails() {
        when(sucursalRepository.save(any(Sucursal.class)))
                .thenThrow(new SecurityException("Error al guardar en la base de datos"));

        assertThatThrownBy(() -> sucursalService.save(sucursalTest))
                .isInstanceOf(SucursalException.class)
                .hasMessageContaining("guardar en la base de datos");

        verify(sucursalRepository, times(1)).save(any(Sucursal.class));
    }

    @Test
    @DisplayName("Listar sucursales")
    public void shouldListSucursales(){
        List<Sucursal> sucursales = Arrays.asList(this.sucursalTest);

        when(sucursalRepository.findAll()).thenReturn(sucursales);

        List<Sucursal> result = this.sucursalService.findAll();

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result).contains(this.sucursalTest);

        verify(sucursalRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Error al listar sucursales")
    public void shouldThrowExceptionWhenListSucursalesFails() {
        when(sucursalRepository.findAll())
                .thenThrow(new SucursalException("Error al listar usuarios"));

        assertThatThrownBy(() -> sucursalService.findAll())
                .isInstanceOf(SucursalException.class)
                .hasMessageContaining("Error al listar");

        verify(sucursalRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Listar sucursales cuando no hay ninguna")
    public void shouldReturnEmptyListWhenNoSucursalesExist() {
        when(sucursalRepository.findAll()).thenReturn(Collections.emptyList());

        List<Sucursal> result = sucursalService.findAll();

        assertThat(result).isNotNull();
        assertThat(result).isEmpty();

        verify(sucursalRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Buscar por id")
    public void shouldBuscarSucursalPorId(){
        when(sucursalRepository.findById(1L)).thenReturn(Optional.of(this.sucursalTest));

        Sucursal result = sucursalService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(sucursalTest);

        verify(sucursalRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Buscar sucursal por ID no encontrado")
    public void shouldThrowExceptionWhenSucursalNotFound() {
        when(sucursalRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sucursalService.findById(999L))
                .isInstanceOf(SucursalException.class)
                .hasMessageContaining("no existe");

        verify(sucursalRepository).findById(999L);
    }

    @Test
    @DisplayName("Error técnico al buscar sucursal por ID")
    public void shouldThrowExceptionWhenFindByIdFails() {
        when(sucursalRepository.findById(anyLong()))
                .thenThrow(new SucursalException("Error técnico al buscar usuario"));

        assertThatThrownBy(() -> sucursalService.findById(1L))
                .isInstanceOf(SucursalException.class)
                .hasMessageContaining("Error técnico");

        verify(sucursalRepository).findById(1L);
    }

    @Test
    @DisplayName("Actualizar sucursales")
    public void shouldActualizarSucursal(){
        Sucursal nuevaSucursal = new Sucursal();
        nuevaSucursal.setNombreSucursal("Sucursal Viña");
        nuevaSucursal.setDireccion("Mall");

        when(sucursalRepository.findById(1L)).thenReturn(Optional.of(this.sucursalTest));
        when(sucursalRepository.save(any(Sucursal.class))).thenReturn(nuevaSucursal);

        Sucursal result = sucursalService.actualizar(1L, nuevaSucursal);

        assertThat(result.getNombreSucursal()).isEqualTo(nuevaSucursal.getNombreSucursal());
        assertThat(result.getDireccion()).isEqualTo(nuevaSucursal.getDireccion());

        verify(sucursalRepository, times(1)).findById(1L);
        verify(sucursalRepository, times(1)).save(any(Sucursal.class));
    }

    @Test
    @DisplayName("Error al actualizar sucursal inexistente")
    public void shouldThrowExceptionWhenUpdatingNonexistentSucursal() {
        when(sucursalRepository.findById(999L)).thenReturn(Optional.empty());

        Sucursal sucursalActualizada = new Sucursal(999L, "nueva sucursal falsa", "calle mentira");

        assertThatThrownBy(() -> sucursalService.actualizar(999L, sucursalActualizada))
                .isInstanceOf(SucursalException.class)
                .hasMessageContaining("no existe");

        verify(sucursalRepository).findById(999L);
        verify(sucursalRepository, never()).save(any());
    }

    @Test
    @DisplayName("Error técnico al actualizar sucursal")
    public void shouldThrowExceptionWhenSaveFailsDuringUpdate() {
        when(sucursalRepository.findById(1L)).thenReturn(Optional.of(sucursalTest));
        when(sucursalRepository.save(any(Sucursal.class)))
                .thenThrow(new SucursalException("Error técnico al guardar"));

        Sucursal sucursalActualizada = new Sucursal(1L, "nueva sucursal", "calle nueva");

        assertThatThrownBy(() -> sucursalService.actualizar(1L, sucursalActualizada))
                .isInstanceOf(SucursalException.class)
                .hasMessageContaining("Error técnico");

        verify(sucursalRepository).findById(1L);
        verify(sucursalRepository).save(any(Sucursal.class));
    }

    @Test
    @DisplayName("Borrar sucursal")
    public void shouldBorrarSucursal(){
        when(sucursalRepository.findById(1L)).thenReturn(Optional.of(sucursalTest));

        sucursalService.borrar(1L);

        verify(sucursalRepository).findById(1L);
        verify(sucursalRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Error al eliminar sucursal inexistente")
    public void shouldThrowExceptionWhenDeletingNonexistentSucursal() {
        when(sucursalRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sucursalService.borrar(999L))
                .isInstanceOf(SucursalException.class)
                .hasMessageContaining("no existe");

        verify(sucursalRepository).findById(999L);
        verify(sucursalRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Error técnico al eliminar sucursal")
    public void shouldThrowExceptionWhenDeleteFails() {
        when(sucursalRepository.findById(1L)).thenReturn(Optional.of(sucursalTest));
        doThrow(new SucursalException("Error técnico al eliminar")).when(sucursalRepository).deleteById(1L);

        assertThatThrownBy(() -> sucursalService.borrar(1L))
                .isInstanceOf(SucursalException.class)
                .hasMessageContaining("Error técnico");

        verify(sucursalRepository).findById(1L);
        verify(sucursalRepository).deleteById(1L);
    }
}
