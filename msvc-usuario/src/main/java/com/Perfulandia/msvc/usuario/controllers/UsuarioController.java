package com.Perfulandia.msvc.usuario.controllers;

import com.Perfulandia.msvc.usuario.models.entities.Usuario;
import com.Perfulandia.msvc.usuario.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@Validated
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    @GetMapping
    public ResponseEntity<List<Usuario>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findById(id));
    }


    @PostMapping
    public ResponseEntity<Usuario> save(@Valid @RequestBody Usuario usuario){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuario));
    }

}
