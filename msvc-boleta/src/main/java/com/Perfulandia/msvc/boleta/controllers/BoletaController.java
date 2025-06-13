package com.Perfulandia.msvc.boleta.controllers;

import com.Perfulandia.msvc.boleta.models.entities.Boleta;
import com.Perfulandia.msvc.boleta.services.BoletaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/boletas")
@Validated
public class BoletaController {

    @Autowired
    private BoletaService boletaService;

    @GetMapping
    public ResponseEntity<List<Boleta>> findAll() {
        return ResponseEntity.ok(boletaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Boleta> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(boletaService.findById(id));
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<Boleta>> findByIdUsuario(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(boletaService.findByIdUsuario(id));
    }

    @GetMapping("/sucursal/{id}")
    public ResponseEntity<List<Boleta>> findByIdSucursal(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(boletaService.findByIdSucursal(id));
    }

    @PostMapping
    public ResponseEntity<Boleta> save(@Valid @RequestBody Boleta boleta) {
        return ResponseEntity.status(HttpStatus.CREATED).body(boletaService.save(boleta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boleta> actualizar(@PathVariable Long id, @RequestBody Boleta nuevaBoleta) {
        Boleta boletaActualizada = boletaService.actualizar(id, nuevaBoleta);
        return ResponseEntity.ok(boletaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        boletaService.borrar(id);
        return ResponseEntity.noContent().build();
    }


}

