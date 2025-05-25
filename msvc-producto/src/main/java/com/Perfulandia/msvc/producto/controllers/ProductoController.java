package com.Perfulandia.msvc.producto.controllers;

import com.Perfulandia.msvc.producto.models.entities.Producto;
import com.Perfulandia.msvc.producto.services.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class ProductoController {

@RestController
@RequestMapping("/api/v1/productos")
@Validated
public class productoController {

    @Autowired
    private ProductoService productoService;


    @GetMapping
    public ResponseEntity<List<Producto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(productoService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Producto> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(productoService.findById(id));
    }


    @PostMapping
    public ResponseEntity<Producto> save(@Valid @RequestBody Producto producto){
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.save(producto));
    }

}

}
