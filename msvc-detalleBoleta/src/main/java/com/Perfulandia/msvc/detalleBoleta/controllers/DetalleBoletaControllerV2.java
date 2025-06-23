package com.Perfulandia.msvc.detalleBoleta.controllers;

import com.Perfulandia.msvc.detalleBoleta.assemblers.DetalleBoletaModelAssembler;
import com.Perfulandia.msvc.detalleBoleta.models.entities.DetalleBoleta;
import com.Perfulandia.msvc.detalleBoleta.services.DetalleBoletaService;
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
@RequestMapping("/api/v2/detalles")
@Validated
@Tag(name = "detalleboletas Hateos", description = "Operaciones detalle boletas Hateos")
public class DetalleBoletaControllerV2 {

    @Autowired
    private final DetalleBoletaService service;

    @Autowired
    private DetalleBoletaModelAssembler detalleBoletaModelAssembler;

    public DetalleBoletaControllerV2(DetalleBoletaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EntityModel<DetalleBoleta>>  create(@Valid @RequestBody DetalleBoleta detalleBoleta) {
        DetalleBoleta detalleBoletaNueva = this.service.guardar(detalleBoleta);
        EntityModel<DetalleBoleta> entityModel = this.detalleBoletaModelAssembler.toModel(detalleBoletaNueva);

        return ResponseEntity
                .created(linkTo(methodOn(DetalleBoletaControllerV2.class).obtener(detalleBoletaNueva.getIdBoleta())).toUri())
                .body(entityModel);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<DetalleBoleta>>> findAll() {
        List<EntityModel<DetalleBoleta>> entityModels = this.service.findAll()
                .stream()
                .map(detalleBoletaModelAssembler::toModel)
                .toList();

        CollectionModel<EntityModel<DetalleBoleta>> collectionModel = CollectionModel.of(
                entityModels,
                linkTo(methodOn(DetalleBoletaControllerV2.class).findAll()).withSelfRel()

        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collectionModel);
    }

    @GetMapping("/boleta/{idBoleta}")
    public ResponseEntity<CollectionModel<EntityModel<DetalleBoleta>>> listarPorBoleta(@PathVariable("idBoleta") Long idBoleta) {
        List<DetalleBoleta> detalleBoletas = service.listarPorBoleta(idBoleta);

        List<EntityModel<DetalleBoleta>> entityModel = detalleBoletas.stream()
                .map(detalleBoletaModelAssembler::toModel)
                .toList();

        CollectionModel<EntityModel<DetalleBoleta>> collectionModel = CollectionModel.of(entityModel);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<DetalleBoleta>> obtener(@PathVariable("id") Long id) {
        EntityModel<DetalleBoleta> entityModel = this.detalleBoletaModelAssembler.toModel(
                this.service.obtener(id)
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entityModel);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable("id") Long id) {
        service.borrar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<DetalleBoleta>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody DetalleBoleta detalleNuevo
    ) {
        DetalleBoleta detalleActualizado = this.service.actualizar(id, detalleNuevo);
        EntityModel<DetalleBoleta> entityModel = this.detalleBoletaModelAssembler.toModel(detalleActualizado);

        return ResponseEntity
                .ok(entityModel);
    }

}

