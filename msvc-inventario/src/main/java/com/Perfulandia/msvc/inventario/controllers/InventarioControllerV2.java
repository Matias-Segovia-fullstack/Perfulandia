package com.Perfulandia.msvc.inventario.controllers;


import com.Perfulandia.msvc.inventario.assemblers.InventarioModelAssembler;
import com.Perfulandia.msvc.inventario.models.entities.Inventario;
import com.Perfulandia.msvc.inventario.services.InventarioService;
import com.Perfulandia.msvc.producto.controllers.ProductoControllerV2;
import com.Perfulandia.msvc.producto.models.entities.Producto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v2/inventarios")
@Validated
@Tag(name = "Inventarios Hateos", description = "Operaciones inventarios")
public class InventarioControllerV2 {

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private InventarioModelAssembler inventarioModelAssembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Inventario>>> findAll() {
        List<EntityModel<Inventario>> entityModels = this.inventarioService.findAll()
                .stream()
                .map(inventarioModelAssembler::toModel)
                .toList();

        CollectionModel<EntityModel<Inventario>> collectionModel = CollectionModel.of(
                entityModels,
                linkTo(methodOn(InventarioControllerV2.class).findAll()).withSelfRel()

        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Inventario>> findById(@PathVariable Long id) {
        EntityModel<Inventario> entityModel = this.inventarioModelAssembler.toModel(
                this.inventarioService.findById(id)
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entityModel);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Inventario>>  create(@Valid @RequestBody Inventario inventario) {
        Inventario inventarioNuevo = this.inventarioService.save(inventario);
        EntityModel<Inventario> entityModel = this.inventarioModelAssembler.toModel(inventarioNuevo);

        return ResponseEntity
                .created(linkTo(methodOn(InventarioControllerV2.class).findById(inventarioNuevo.getIdInventario())).toUri())
                .body(entityModel);
    }

    @GetMapping("/sucursal/{id}")
    public ResponseEntity<CollectionModel<EntityModel<Inventario>>> findByIdSucursal(@PathVariable("id") Long id) {
        List<Inventario> inventarios = inventarioService.findByIdSucursal(id);

        List<EntityModel<Inventario>> entityModel = inventarios
                .stream()
                .map(inventarioModelAssembler::toModel)
                .toList();

        CollectionModel<EntityModel<Inventario>> collectionModel = CollectionModel.of(entityModel);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collectionModel);
    }

    @GetMapping("/producto/{id}")
    public ResponseEntity<CollectionModel<EntityModel<Inventario>>> findByIdProducto(@PathVariable("id") Long id) {
        List<Inventario> inventarios = inventarioService.findByIdProducto(id);

        List<EntityModel<Inventario>> entityModel = inventarios.stream()
                .map(inventarioModelAssembler::toModel)
                .toList();

        CollectionModel<EntityModel<Inventario>> collectionModel = CollectionModel.of(entityModel);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collectionModel);
    }

    @GetMapping("/sucursal/{idSucursal}/producto/{idProducto}")
    public ResponseEntity<EntityModel<Inventario>> findByProductAndSucursal(
            @PathVariable("idSucursal") Long idSucursal,
            @PathVariable("idProducto") Long idProducto) {
        Inventario inventario = inventarioService.findByProductoAndSucursal(idSucursal, idProducto);

        EntityModel<Inventario> entityModel = inventarioModelAssembler.toModel(inventario);

        return ResponseEntity.status(HttpStatus.OK).body(entityModel);
    }


    @PutMapping("/sucursal/{idSucursal}/producto/{idProducto}")
    public ResponseEntity<Inventario> actualizarStock(
            @PathVariable("idSucursal") Long idSucursal,
            @PathVariable("idProducto") Long idProducto,
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