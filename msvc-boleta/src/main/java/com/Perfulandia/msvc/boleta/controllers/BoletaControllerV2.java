package com.Perfulandia.msvc.boleta.controllers;

import com.Perfulandia.msvc.boleta.assemblers.BoletaModelAssembler;
import com.Perfulandia.msvc.boleta.models.entities.Boleta;
import com.Perfulandia.msvc.boleta.services.BoletaService;
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
@RequestMapping("/api/v2/boletas")
@Validated
@Tag(name = "boletas Hateos", description = "Operaciones boletas Hateos")
public class BoletaControllerV2 {

    @Autowired
    private BoletaService boletaService;

    @Autowired
    private BoletaModelAssembler boletaModelAssembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Boleta>>> findAll() {
        List<EntityModel<Boleta>> entityModels = this.boletaService.findAll()
                .stream()
                .map(boletaModelAssembler::toModel)
                .toList();

        CollectionModel<EntityModel<Boleta>> collectionModel = CollectionModel.of(
                entityModels,
                linkTo(methodOn(BoletaControllerV2.class).findAll()).withSelfRel()

        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Boleta>> findById(@PathVariable Long id) {
        EntityModel<Boleta> entityModel = this.boletaModelAssembler.toModel(
                this.boletaService.findById(id)
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entityModel);
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<CollectionModel<EntityModel<Boleta>>> findByIdUsuario(@PathVariable("id") Long id) {
        List<Boleta> boletas = boletaService.findByIdUsuario(id);

        List<EntityModel<Boleta>> entityModel = boletas.stream()
                .map(boletaModelAssembler::toModel)
                .toList();

        CollectionModel<EntityModel<Boleta>> collectionModel = CollectionModel.of(entityModel);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collectionModel);
    }

    @GetMapping("/sucursal/{id}")
    public ResponseEntity<CollectionModel<EntityModel<Boleta>>> findByIdSucursal(@PathVariable("id") Long id) {
        List<Boleta> boletas = boletaService.findByIdSucursal(id);

        List<EntityModel<Boleta>> entityModel = boletas
                .stream()
                .map(boletaModelAssembler::toModel)
                .toList();

        CollectionModel<EntityModel<Boleta>> collectionModel = CollectionModel.of(entityModel);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collectionModel);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Boleta>>  create(@Valid @RequestBody Boleta boleta) {
        Boleta boletaNueva = this.boletaService.save(boleta);
        EntityModel<Boleta> entityModel = this.boletaModelAssembler.toModel(boletaNueva);

        return ResponseEntity
                .created(linkTo(methodOn(BoletaControllerV2.class).findById(boletaNueva.getIdBoleta())).toUri())
                .body(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Boleta>> update(
            @PathVariable Long id,
            @Valid @RequestBody Boleta boletaNueva
    ) {
        Boleta boletaActualizada = this.boletaService.actualizar(id, boletaNueva);
        EntityModel<Boleta> entityModel = this.boletaModelAssembler.toModel(boletaActualizada);

        return ResponseEntity
                .ok(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        boletaService.borrar(id);
        return ResponseEntity.noContent().build();
    }


}

