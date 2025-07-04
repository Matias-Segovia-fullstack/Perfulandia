package com.Perfulandia.msvc.boleta.services;

import com.Perfulandia.msvc.boleta.dto.UsuarioDTO;
import com.Perfulandia.msvc.boleta.dto.SucursalDTO;
import com.Perfulandia.msvc.boleta.clients.SucursalClient;
import com.Perfulandia.msvc.boleta.clients.UsuarioClient;
import com.Perfulandia.msvc.boleta.exceptions.BoletaException;
import com.Perfulandia.msvc.boleta.models.entities.Boleta;
import com.Perfulandia.msvc.boleta.repositories.BoletaRepository;
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

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BoletaServiceTest {

    @Mock
    private BoletaRepository boletaRepository;

    @InjectMocks
    private BoletaServiceImpl boletaService;

    private Boleta boletaTest;

    @Mock
    private UsuarioClient usuarioClient;

    @Mock
    private SucursalClient sucursalClient;


    @BeforeEach
    void setUp() {
        boletaTest = new Boleta();
        boletaTest.setIdBoleta(1L);
        boletaTest.setIdUsuario(10L);
        boletaTest.setIdSucursal(5L);
        boletaTest.setTotal(2500.0);
    }

    @Test
    @DisplayName("Actualizar boleta existente correctamente")
    void shouldUpdateBoleta() {
        when(boletaRepository.findById(1L)).thenReturn(Optional.of(new Boleta()));
        when(boletaRepository.save(any(Boleta.class))).thenReturn(boletaTest);

        Boleta resultado = boletaService.actualizar(1L, boletaTest);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getIdUsuario()).isEqualTo(10L);
        assertThat(resultado.getIdSucursal()).isEqualTo(5L);
        assertThat(resultado.getTotal()).isEqualTo(2500.0);

        verify(boletaRepository).findById(1L);
        verify(boletaRepository).save(any(Boleta.class));
    }

    @Test
    @DisplayName("Error al actualizar boleta inexistente")
    void shouldThrowExceptionWhenUpdatingNonexistentBoleta() {
        when(boletaRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> boletaService.actualizar(999L, boletaTest))
                .isInstanceOf(BoletaException.class)
                .hasMessageContaining("No existe");

        verify(boletaRepository).findById(999L);
        verify(boletaRepository, never()).save(any());
    }

    @Test
    @DisplayName("Eliminar boleta existente correctamente")
    void shouldDeleteBoleta() {
        when(boletaRepository.findById(1L)).thenReturn(Optional.of(boletaTest));

        boletaService.borrar(1L);

        verify(boletaRepository).findById(1L);
        verify(boletaRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Error al eliminar boleta inexistente")
    void shouldThrowExceptionWhenDeletingNonexistentBoleta() {
        when(boletaRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> boletaService.borrar(999L))
                .isInstanceOf(BoletaException.class)
                .hasMessageContaining("No existe");

        verify(boletaRepository).findById(999L);
        verify(boletaRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Guardar boleta correctamente")
    void shouldSaveBoleta() {
        when(usuarioClient.obtenerUsuario(10L)).thenReturn(new UsuarioDTO(10L, "usuarioTest", "12345678-9"));
        when(sucursalClient.obtenerSucursal(5L)).thenReturn(new SucursalDTO(5L, "Sucursal Central", "Av. Siempre Viva 123"));
        when(boletaRepository.save(boletaTest)).thenReturn(boletaTest);

        Boleta resultado = boletaService.save(boletaTest);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getTotal()).isEqualTo(2500.0);
        verify(boletaRepository).save(boletaTest);
    }


    @Test
    @DisplayName("Buscar boleta por ID correctamente")
    void shouldFindBoletaById() {
        when(boletaRepository.findById(1L)).thenReturn(Optional.of(boletaTest));

        Boleta resultado = boletaService.findById(1L);

        assertThat(resultado).isEqualTo(boletaTest);
        verify(boletaRepository).findById(1L);
    }

    @Test
    @DisplayName("Buscar todas las boletas")
    void shouldReturnAllBoletas() {
        when(boletaRepository.findAll()).thenReturn(Arrays.asList(boletaTest));

        List<Boleta> lista = boletaService.findAll();

        assertThat(lista).hasSize(1);
        verify(boletaRepository).findAll();
    }

    @Test
    @DisplayName("Buscar boletas por ID de usuario")
    void shouldFindBoletasByIdUsuario() {
        when(boletaRepository.findByIdUsuario(10L)).thenReturn(List.of(boletaTest));

        List<Boleta> lista = boletaService.findByIdUsuario(10L);

        assertThat(lista).contains(boletaTest);
        verify(boletaRepository).findByIdUsuario(10L);
    }
}

