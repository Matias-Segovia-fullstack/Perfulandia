package com.Perfulandia.msvc.boleta.clients;

import com.Perfulandia.msvc.boleta.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-usuario", url = "${msvc.usuario.url}")
public interface UsuarioClient {
    @GetMapping("/api/v1/usuarios/{id}")
    UsuarioDTO obtenerUsuario(@PathVariable("id") Long id);
}

