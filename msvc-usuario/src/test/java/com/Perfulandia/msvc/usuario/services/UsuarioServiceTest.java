package com.Perfulandia.msvc.usuario.services;

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
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    @DisplayName("Buscar por id")
    public void shouldBuscarUsuarioPorId(){
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(this.usuarioTest));

        Usuario result = usuarioService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(usuarioTest);

        verify(usuarioRepository, times(1)).findById(1L);
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
    @DisplayName("Borrar usuario")
    public void shouldBorrarUsuario(){
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioTest));

        usuarioService.borrar(1L);

        verify(usuarioRepository).findById(1L);
        verify(usuarioRepository).deleteById(1L);
    }


}
