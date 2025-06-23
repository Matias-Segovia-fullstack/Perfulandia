package com.Perfulandia.msvc.producto.controllers;

import com.Perfulandia.msvc.producto.assemblers.ProductoModelAssembler;
import com.Perfulandia.msvc.producto.models.entities.Producto;
import com.Perfulandia.msvc.producto.services.ProductoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/productos")
@Validated
@Tag(name = "Productos", description = "Operaciones productos Hateos")
public class ProductoControllerV2 {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoModelAssembler productoModelAssembler;

    @GetMapping
    public ResponseEntity<List<Producto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(productoService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Producto> findById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(productoService.findById(id));
    }


    @PostMapping
    public ResponseEntity<Producto> save(@Valid @RequestBody Producto producto){
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.save(producto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto nuevoProducto){
        Producto productoActualizado = productoService.actualizar(id, nuevoProducto);
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id){
        productoService.borrar(id);
        return ResponseEntity.noContent().build();
    }

}


