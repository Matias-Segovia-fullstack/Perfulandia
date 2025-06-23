package com.Perfulandia.msvc.producto.controllers;

import com.Perfulandia.msvc.producto.assemblers.ProductoModelAssembler;
import com.Perfulandia.msvc.producto.models.entities.Producto;
import com.Perfulandia.msvc.producto.services.ProductoService;
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
@RequestMapping("/api/v2/productos")
@Validated
@Tag(name = "Productos Hateos", description = "Operaciones productos Hateos")
public class ProductoControllerV2 {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoModelAssembler productoModelAssembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> findAll() {
        List<EntityModel<Producto>> entityModels = this.productoService.findAll()
                .stream()
                .map(productoModelAssembler::toModel)
                .toList();

        CollectionModel<EntityModel<Producto>> collectionModel = CollectionModel.of(
                entityModels,
                linkTo(methodOn(ProductoControllerV2.class).findAll()).withSelfRel()

        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collectionModel);
    }


    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Producto>> findById(@PathVariable Long id) {
        EntityModel<Producto> entityModel = this.productoModelAssembler.toModel(
                this.productoService.findById(id)
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entityModel);
    }


    @PostMapping
    public ResponseEntity<EntityModel<Producto>>  create(@Valid @RequestBody Producto producto) {
        Producto productoNuevo = this.productoService.save(producto);
        EntityModel<Producto> entityModel = this.productoModelAssembler.toModel(productoNuevo);

        return ResponseEntity
                .created(linkTo(methodOn(ProductoControllerV2.class).findById(productoNuevo.getIdProducto())).toUri())
                .body(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Producto>> update(
            @PathVariable Long id,
            @Valid @RequestBody Producto productoNuevo
    ) {
        Producto productoActualizado = this.productoService.actualizar(id, productoNuevo);
        EntityModel<Producto> entityModel = this.productoModelAssembler.toModel(productoActualizado);

        return ResponseEntity
                .ok(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id){
        productoService.borrar(id);
        return ResponseEntity.noContent().build();
    }

}


