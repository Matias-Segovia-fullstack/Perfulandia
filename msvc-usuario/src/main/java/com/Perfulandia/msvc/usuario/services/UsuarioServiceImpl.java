package com.Perfulandia.msvc.usuario.services;

import com.Perfulandia.msvc.usuario.exceptions.UsuarioException;
import com.Perfulandia.msvc.usuario.models.entities.Usuario;
import com.Perfulandia.msvc.usuario.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> findAll() {
        return this.usuarioRepository.findAll();
    }

    @Override
    public Usuario findById(Long id) {
        return this.usuarioRepository.findById(id).orElseThrow(
                () ->new UsuarioException("El usuario con id "+id+" no existe")
        );
    }

    @Override
    public Usuario save(Usuario usuario) {
        return this.usuarioRepository.save(usuario);
    }

    @Override
    public Usuario actualizar(Long id, Usuario nuevoUsuario){
        Usuario usuarioTemp = usuarioRepository.findById(id).orElseThrow(
                () ->new UsuarioException("El usuario con id "+id+" no existe"));
        usuarioTemp.setNombreUsuario(nuevoUsuario.getNombreUsuario());
        usuarioTemp.setRut(nuevoUsuario.getRut());

        return this.usuarioRepository.save(usuarioTemp);
    }

    @Override
    public void borrar(Long id){
        Usuario usuarioTemp = usuarioRepository.findById(id).orElseThrow(
                () ->new UsuarioException("El usuario con id "+id+" no existe"));
        usuarioRepository.deleteById(id);
    }
}
