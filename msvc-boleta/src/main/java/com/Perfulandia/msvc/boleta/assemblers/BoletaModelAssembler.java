package com.Perfulandia.msvc.boleta.assemblers;

import com.Perfulandia.msvc.boleta.controllers.BoletaController;
import com.Perfulandia.msvc.boleta.controllers.BoletaControllerV2;
import com.Perfulandia.msvc.boleta.models.entities.Boleta;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Component
public class BoletaModelAssembler implements RepresentationModelAssembler<Boleta, EntityModel<Boleta>> {

    @Override
    public EntityModel<Boleta> toModel(Boleta entity) {
        return EntityModel.of(
                entity,
                linkTo(methodOn(BoletaControllerV2.class).findById(entity.getIdBoleta())).withSelfRel(),
                linkTo(methodOn(BoletaControllerV2.class).findAll()).withRel("usuarios"),
                Link.of("http://localhost:8004/api/v1/sucursal/"+entity.getIdSucursal()).withRel("sucursal"),
                Link.of("http://localhost:8005/api/v1/usuario/"+entity.getIdUsuario()).withRel("usuario")
        );

    }
}
