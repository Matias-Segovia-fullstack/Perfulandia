package com.Perfulandia.msvc.inventario.controllers;


import com.Perfulandia.msvc.inventario.models.entities.Inventario;
import com.Perfulandia.msvc.inventario.services.InventarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventario")
@Validated
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;


    @GetMapping
    public ResponseEntity<List<Inventario>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(inventarioService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Inventario> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(inventarioService.findById(id));
    }


    @PostMapping
    public ResponseEntity<Inventario> save(@Valid @RequestBody Inventario inventario){
        return ResponseEntity.status(HttpStatus.CREATED).body(inventarioService.save(inventario));
    }

}
