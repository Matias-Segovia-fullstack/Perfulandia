package com.Perfulandia.msvc.inventario.controllers;


import com.Perfulandia.msvc.inventario.exceptions.InventarioException;
import com.Perfulandia.msvc.inventario.models.entities.Inventario;
import com.Perfulandia.msvc.inventario.services.InventarioService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventarios")
@Validated
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    public ResponseEntity<List<Inventario>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(inventarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventario> findById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(inventarioService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Inventario> save(@Valid @RequestBody Inventario inventario){
        return ResponseEntity.status(HttpStatus.CREATED).body(inventarioService.save(inventario));
    }

    @GetMapping("/sucursal/{id}")
    public ResponseEntity<List<Inventario>> findByIdSucursal(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(inventarioService.findByIdSucursal(id));
    }

    @GetMapping("/producto/{id}")
    public ResponseEntity<List<Inventario>> findByIdProducto(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(inventarioService.findByIdProducto(id));
    }

    @GetMapping("/sucursal/{idSucursal}/producto/{idProducto}")
    public ResponseEntity<Inventario> findByProductAndSucursal(
            @PathVariable("idSucursal") Long idSucursal,
            @PathVariable("idProducto") Long idProducto) {
        return ResponseEntity.status(HttpStatus.OK).body(inventarioService.findByProductAndSucursal(idSucursal, idProducto));
    }

    @PutMapping("/sucursal/{idSucursal}/producto/{idProducto}")
    public ResponseEntity<Inventario> actualizarStock(
            @PathVariable Long idSucursal,
            @PathVariable Long idProducto,
            @RequestBody Integer nuevoStock) {
        Inventario inventarioActualizado = inventarioService.actualizarStock(idSucursal, idProducto, nuevoStock);
        return ResponseEntity.ok(inventarioActualizado);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id){
        inventarioService.borrar(id);
        return ResponseEntity.noContent().build();
    }
}