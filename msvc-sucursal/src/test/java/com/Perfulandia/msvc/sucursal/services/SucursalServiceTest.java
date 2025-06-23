package com.Perfulandia.msvc.sucursal.services;

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
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    @DisplayName("Buscar por id")
    public void shouldBuscarSucursalPorId(){
        when(sucursalRepository.findById(1L)).thenReturn(Optional.of(this.sucursalTest));

        Sucursal result = sucursalService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(sucursalTest);

        verify(sucursalRepository, times(1)).findById(1L);
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
    @DisplayName("Borrar sucursal")
    public void shouldBorrarSucursal(){
        when(sucursalRepository.findById(1L)).thenReturn(Optional.of(sucursalTest));

        sucursalService.borrar(1L);

        verify(sucursalRepository).findById(1L);
        verify(sucursalRepository).deleteById(1L);
    }
}
