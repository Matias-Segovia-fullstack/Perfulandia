package com.Perfulandia.msvc.boleta.services;

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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BoletaServiceTest {

    @Mock
    private BoletaRepository boletaRepository;

    @InjectMocks
    private BoletaServiceImpl boletaService;

    private Boleta boletaTest;

    @BeforeEach
    public void setUp() {
        boletaTest = new Boleta();
        boletaTest.setIdBoleta(1L);
        boletaTest.setIdUsuario(10L);
        boletaTest.setIdSucursal(5L);
        boletaTest.setTotal(2500.0);
    }

    @Test
    @DisplayName("Actualizar boleta existente correctamente")
    public void shouldUpdateBoleta() {
        when(boletaRepository.findById(1L)).thenReturn(Optional.of(new Boleta()));
        when(boletaRepository.save(any(Boleta.class))).thenReturn(boletaTest);

        Boleta resultado = boletaService.actualizar(1L, boletaTest);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getIdUsuario()).isEqualTo(10L);
        assertThat(resultado.getIdSucursal()).isEqualTo(5L);
        assertThat(resultado.getTotal()).isEqualTo(2500.0);

        verify(boletaRepository, times(1)).findById(1L);
        verify(boletaRepository, times(1)).save(any(Boleta.class));
    }

    @Test
    @DisplayName("Error al actualizar boleta inexistente")
    public void shouldThrowExceptionWhenUpdatingNonexistentBoleta() {
        when(boletaRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> boletaService.borrar(999L))
                .isInstanceOf(BoletaException.class)
                .hasMessageContaining("No existe");


        verify(boletaRepository, times(1)).findById(999L);
        verify(boletaRepository, never()).save(any());
    }

    @Test
    @DisplayName("Eliminar boleta existente correctamente")
    public void shouldDeleteBoleta() {
        when(boletaRepository.findById(1L)).thenReturn(Optional.of(boletaTest));

        boletaService.borrar(1L);

        verify(boletaRepository, times(1)).findById(1L);
        verify(boletaRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Error al eliminar boleta inexistente")
    public void shouldThrowExceptionWhenDeletingNonexistentBoleta() {
        when(boletaRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> boletaService.borrar(999L))
                .isInstanceOf(BoletaException.class)
                .hasMessageContaining("No existe");


        verify(boletaRepository, times(1)).findById(999L);
        verify(boletaRepository, never()).deleteById(any());
    }
}
