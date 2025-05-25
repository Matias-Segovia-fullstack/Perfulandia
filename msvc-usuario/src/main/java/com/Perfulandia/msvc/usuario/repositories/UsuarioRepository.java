package com.Perfulandia.msvc.usuario.repositories;

import com.Perfulandia.msvc.usuario.models.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
