package com.Perfulandia.msvc.detalleBoleta.assemblers;

import com.Perfulandia.msvc.detalleBoleta.controllers.DetalleBoletaControllerV2;
import com.Perfulandia.msvc.detalleBoleta.models.entities.DetalleBoleta;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class DetalleBoletaModelAssembler implements RepresentationModelAssembler<DetalleBoleta, EntityModel<DetalleBoleta>> {

    @Override
    public EntityModel<DetalleBoleta> toModel(DetalleBoleta entity) {
        return EntityModel.of(
                entity,
                linkTo(methodOn(DetalleBoletaControllerV2.class).obtener(entity.getIdBoleta())).withSelfRel(),
                linkTo(methodOn(DetalleBoletaControllerV2.class).findAll()).withRel("detalles de boletas"),
                Link.of("http://localhost:8002/api/v1/producto/"+entity.getProductoId()).withRel("producto"),
                Link.of("http://localhost:8001/api/v1/boleta/"+entity.getIdBoleta()).withRel("boleta")
        );

    }
}
