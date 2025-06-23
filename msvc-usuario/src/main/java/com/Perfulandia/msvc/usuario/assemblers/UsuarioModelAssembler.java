package com.Perfulandia.msvc.usuario.assemblers;

import com.Perfulandia.msvc.usuario.controllers.UsuarioControllerV2;
import com.Perfulandia.msvc.usuario.models.entities.Usuario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {
    @Override
    public EntityModel<Usuario> toModel(Usuario entity) {

        return EntityModel.of(
                entity,
                linkTo(methodOn(UsuarioControllerV2.class).findById(entity.getIdUsuario())).withSelfRel(),
                linkTo(methodOn(UsuarioControllerV2.class).findAll()).withRel("usuarios")
        );
    }
}
