package com.Perfulandia.msvc.inventario.assemblers;

import com.Perfulandia.msvc.inventario.controllers.InventarioControllerV2;
import com.Perfulandia.msvc.inventario.models.entities.Inventario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class InventarioModelAssembler implements RepresentationModelAssembler<Inventario, EntityModel<Inventario>> {

    @Override
    public EntityModel<Inventario> toModel(Inventario entity) {
        return EntityModel.of(
                entity,
                linkTo(methodOn(InventarioControllerV2.class).findById(entity.getIdProducto())).withSelfRel(),
                linkTo(methodOn(InventarioControllerV2.class).findAll()).withRel("inventarios"),
                Link.of("http://localhost:8004/api/v1/sucursal/"+entity.getIdSucursal()).withRel("sucursal"),
                Link.of("http://localhost:8002/api/v1/producto/"+entity.getIdProducto()).withRel("producto")
        );

    }
}
