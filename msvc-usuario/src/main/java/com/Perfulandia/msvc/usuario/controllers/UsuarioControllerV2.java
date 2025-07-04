package com.Perfulandia.msvc.usuario.controllers;

import com.Perfulandia.msvc.usuario.assemblers.UsuarioModelAssembler;
import com.Perfulandia.msvc.usuario.models.entities.Usuario;
import com.Perfulandia.msvc.usuario.services.UsuarioService;
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
@RequestMapping("/api/v2/usuarios")
@Validated
@Tag(name = "Usuarios Hateos", description = "Operaciones usuarios Hateos")
public class UsuarioControllerV2 {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Usuario>>> findAll() {
        List<EntityModel<Usuario>> entityModels = this.usuarioService.findAll()
                .stream()
                .map(usuarioModelAssembler::toModel)
                .toList();

        CollectionModel<EntityModel<Usuario>> collectionModel = CollectionModel.of(
                entityModels,
                linkTo(methodOn(UsuarioControllerV2.class).findAll()).withSelfRel()

        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collectionModel);
    }



    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Usuario>> findById(@PathVariable Long id) {
        EntityModel<Usuario> entityModel = this.usuarioModelAssembler.toModel(
                this.usuarioService.findById(id)
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entityModel);
    }


    @PostMapping
    public ResponseEntity<EntityModel<Usuario>>  create(@Valid @RequestBody Usuario usuario) {
        Usuario usuarioNuevo = this.usuarioService.save(usuario);
        EntityModel<Usuario> entityModel = this.usuarioModelAssembler.toModel(usuarioNuevo);

        return ResponseEntity
                .created(linkTo(methodOn(UsuarioControllerV2.class).findById(usuarioNuevo.getIdUsuario())).toUri())
                .body(entityModel);
    }


    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Usuario>> update(
            @PathVariable Long id,
            @Valid @RequestBody Usuario usuarioNuevo
    ) {
        Usuario usuarioActualizado = this.usuarioService.actualizar(id, usuarioNuevo);
        EntityModel<Usuario> entityModel = this.usuarioModelAssembler.toModel(usuarioActualizado);

        return ResponseEntity
                .ok(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id){
        usuarioService.borrar(id);
        return ResponseEntity.noContent().build();
    }

}
