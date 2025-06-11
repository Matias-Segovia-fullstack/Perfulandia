package com.Perfulandia.msvc.inventario.controllers;


import com.Perfulandia.msvc.inventario.exceptions.InventarioException;
import com.Perfulandia.msvc.inventario.models.entities.Inventario;
import com.Perfulandia.msvc.inventario.services.InventarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventario")
@Validated
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;


    @PutMapping("/{idProducto}/stock")
    public ResponseEntity<Inventario> actualizarStock(@PathVariable("idProducto") Long idProducto,@RequestParam int cantidad){
        return ResponseEntity.status(HttpStatus.OK).body(inventarioService.actualizarStock(idProducto, cantidad));
    }



    }

    @PostMapping
    public ResponseEntity<Inventario> save(@Valid @RequestBody Inventario inventario){
        return ResponseEntity.status(HttpStatus.CREATED).body(inventarioService.save(inventario));
    }

}