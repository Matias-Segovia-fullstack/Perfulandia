package com.Perfulandia.msvc.sucursal.controllers;

import com.Perfulandia.msvc.sucursal.assemblers.SucursalModelAssembler;
import com.Perfulandia.msvc.sucursal.models.entities.Sucursal;
import com.Perfulandia.msvc.sucursal.services.SucursalService;
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
@RequestMapping("/api/v2/sucursales")
@Validated
@Tag(name = "Sucursales Hateos", description = "Operaciones sucursales Hateos")
public class SucursalControllerV2 {

    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private SucursalModelAssembler sucursalModelAssembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Sucursal>>> findAll() {
        List<EntityModel<Sucursal>> entityModels = this.sucursalService.findAll()
                .stream()
                .map(sucursalModelAssembler::toModel)
                .toList();

        CollectionModel<EntityModel<Sucursal>> collectionModel = CollectionModel.of(
                entityModels,
                linkTo(methodOn(SucursalControllerV2.class).findAll()).withSelfRel()

        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collectionModel);
    }


    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Sucursal>> findById(@PathVariable Long id) {
        EntityModel<Sucursal> entityModel = this.sucursalModelAssembler.toModel(
                this.sucursalService.findById(id)
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entityModel);
    }


    @PostMapping
    public ResponseEntity<EntityModel<Sucursal>>  create(@Valid @RequestBody Sucursal sucursal) {
        Sucursal sucuersalNueva = this.sucursalService.save(sucursal);
        EntityModel<Sucursal> entityModel = this.sucursalModelAssembler.toModel(sucuersalNueva);

        return ResponseEntity
                .created(linkTo(methodOn(SucursalControllerV2.class).findById(sucuersalNueva.getIdSucursal())).toUri())
                .body(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Sucursal>> update(
            @PathVariable Long id,
            @Valid @RequestBody Sucursal sucursalNueva
    ) {
        Sucursal sucursalActualizada = this.sucursalService.actualizar(id, sucursalNueva);
        EntityModel<Sucursal> entityModel = this.sucursalModelAssembler.toModel(sucursalActualizada);

        return ResponseEntity
                .ok(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        sucursalService.borrar(id);
        return ResponseEntity.noContent().build();
    }
}
