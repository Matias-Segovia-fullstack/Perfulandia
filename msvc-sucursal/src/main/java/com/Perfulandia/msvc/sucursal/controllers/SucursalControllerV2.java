package com.Perfulandia.msvc.sucursal.controllers;

import com.Perfulandia.msvc.sucursal.assemblers.SucursalModelAssembler;
import com.Perfulandia.msvc.sucursal.models.entities.Sucursal;
import com.Perfulandia.msvc.sucursal.services.SucursalService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/sucursales")
@Validated
@Tag(name = "Sucursales", description = "Operaciones sucursales Hateos")
public class SucursalControllerV2 {

    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private SucursalModelAssembler sucursalModelAssembler;

    @GetMapping
    public ResponseEntity<List<Sucursal>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(sucursalService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Sucursal> findById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(sucursalService.findById(id));
    }


    @PostMapping
    public ResponseEntity<Sucursal> save(@Valid @RequestBody Sucursal sucursal){
        return ResponseEntity.status(HttpStatus.CREATED).body(sucursalService.save(sucursal));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sucursal> actualizar(@PathVariable Long id, @RequestBody Sucursal nuevaSucursal){
        Sucursal sucursalActualizada = sucursalService.actualizar(id, nuevaSucursal);
        return ResponseEntity.ok(sucursalActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        sucursalService.borrar(id);
        return ResponseEntity.noContent().build();
    }
}
