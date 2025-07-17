package com.Perfulandia.msvc.usuario.services;

import com.Perfulandia.msvc.usuario.exceptions.UsuarioException;
import com.Perfulandia.msvc.usuario.models.entities.Usuario;
import com.Perfulandia.msvc.usuario.repositories.UsuarioRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private Usuario usuarioTest;

    @BeforeEach
    public void setUp(){
        usuarioTest = new Usuario(
                1L, "Cristiano Ronaldo", "19.652.723-4"
        );
    }

    @Test
    @DisplayName("Crear usuario")
    public void shouldCreateUsuario(){
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioTest);

        Usuario result = usuarioService.save(usuarioTest);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(this.usuarioTest);

        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Error al crear usuario")
    public void shouldThrowExceptionWhenSaveFails() {
        when(usuarioRepository.save(any(Usuario.class)))
                .thenThrow(new UsuarioException("Error al guardar en la base de datos"));

        assertThatThrownBy(() -> usuarioService.save(usuarioTest))
                .isInstanceOf(UsuarioException.class)
                .hasMessageContaining("guardar en la base de datos");

        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Listar usuarios")
    public void shouldListInventarios(){
        List<Usuario> usuarios = Arrays.asList(this.usuarioTest);

        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<Usuario> result = this.usuarioService.findAll();

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result).contains(this.usuarioTest);

        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Error al listar usuarios")
    public void shouldThrowExceptionWhenListUsuariosFails() {
        when(usuarioRepository.findAll())
                .thenThrow(new UsuarioException("Error al listar usuarios"));

        assertThatThrownBy(() -> usuarioService.findAll())
                .isInstanceOf(UsuarioException.class)
                .hasMessageContaining("Error al listar");

        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Listar usuarios cuando no hay ninguno")
    public void shouldReturnEmptyListWhenNoUsuariosExist() {
        when(usuarioRepository.findAll()).thenReturn(Collections.emptyList());

        List<Usuario> result = usuarioService.findAll();

        assertThat(result).isNotNull();
        assertThat(result).isEmpty();

        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Buscar por id")
    public void shouldBuscarUsuarioPorId(){
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(this.usuarioTest));

        Usuario result = usuarioService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(usuarioTest);

        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Buscar usuario por ID no encontrado")
    public void shouldThrowExceptionWhenUsuarioNotFound() {
        when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usuarioService.findById(999L))
                .isInstanceOf(UsuarioException.class)
                .hasMessageContaining("no existe");

        verify(usuarioRepository).findById(999L);
    }

    @Test
    @DisplayName("Error técnico al buscar usuario por ID")
    public void shouldThrowExceptionWhenFindByIdFails() {
        when(usuarioRepository.findById(anyLong()))
                .thenThrow(new UsuarioException("Error técnico al buscar usuario"));

        assertThatThrownBy(() -> usuarioService.findById(1L))
                .isInstanceOf(UsuarioException.class)
                .hasMessageContaining("Error técnico");

        verify(usuarioRepository).findById(1L);
    }

    @Test
    @DisplayName("Actualizar usuario")
    public void shouldActualizarUsuario(){
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombreUsuario("Don Francisco");
        nuevoUsuario.setRut("03.812.712-5");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(this.usuarioTest));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(nuevoUsuario);

        Usuario result = usuarioService.actualizar(1L, nuevoUsuario);

        assertThat(result.getNombreUsuario()).isEqualTo(nuevoUsuario.getNombreUsuario());
        assertThat(result.getRut()).isEqualTo(nuevoUsuario.getRut());

        verify(usuarioRepository, times(1)).findById(1L);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Error al actualizar usuario inexistente")
    public void shouldThrowExceptionWhenUpdatingNonexistentUsuario() {
        when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        Usuario usuarioActualizado = new Usuario(999L, "Nombre", "19.652.723-4");

        assertThatThrownBy(() -> usuarioService.actualizar(999L, usuarioActualizado))
                .isInstanceOf(UsuarioException.class)
                .hasMessageContaining("no existe");

        verify(usuarioRepository).findById(999L);
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Error técnico al actualizar usuario")
    public void shouldThrowExceptionWhenSaveFailsDuringUpdate() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioTest));
        when(usuarioRepository.save(any(Usuario.class)))
                .thenThrow(new UsuarioException("Error técnico al guardar"));

        Usuario usuarioActualizado = new Usuario(1L, "Nuevo Nombre", "19.652.723-4");

        assertThatThrownBy(() -> usuarioService.actualizar(1L, usuarioActualizado))
                .isInstanceOf(UsuarioException.class)
                .hasMessageContaining("Error técnico");

        verify(usuarioRepository).findById(1L);
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Borrar usuario")
    public void shouldBorrarUsuario(){
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioTest));

        usuarioService.borrar(1L);

        verify(usuarioRepository).findById(1L);
        verify(usuarioRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Error al eliminar usuario inexistente")
    public void shouldThrowExceptionWhenDeletingNonexistentUsuario() {
        when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usuarioService.borrar(999L))
                .isInstanceOf(UsuarioException.class)
                .hasMessageContaining("no existe");

        verify(usuarioRepository).findById(999L);
        verify(usuarioRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Error técnico al eliminar usuario")
    public void shouldThrowExceptionWhenDeleteFails() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioTest));
        doThrow(new UsuarioException("Error técnico al eliminar")).when(usuarioRepository).deleteById(1L);

        assertThatThrownBy(() -> usuarioService.borrar(1L))
                .isInstanceOf(UsuarioException.class)
                .hasMessageContaining("Error técnico");

        verify(usuarioRepository).findById(1L);
        verify(usuarioRepository).deleteById(1L);
    }



}
