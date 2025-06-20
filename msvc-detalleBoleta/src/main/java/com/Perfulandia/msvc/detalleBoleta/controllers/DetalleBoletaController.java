package com.Perfulandia.msvc.detalleBoleta.controllers;

import com.Perfulandia.msvc.detalleBoleta.models.entities.DetalleBoleta;
import com.Perfulandia.msvc.detalleBoleta.services.DetalleBoletaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/detalles")
public class DetalleBoletaController {

    private final DetalleBoletaService service;

    public DetalleBoletaController(DetalleBoletaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DetalleBoleta> crear(@Valid @RequestBody DetalleBoleta detalle) {
        DetalleBoleta guardado = service.guardar(detalle);
        return ResponseEntity.created(URI.create("/api/v1/detalles/" + guardado.getId())).body(guardado);
    }

    @GetMapping("/boleta/{idBoleta}")
    public ResponseEntity<List<DetalleBoleta>> listarPorBoleta(@PathVariable("idBoleta") Long idBoleta) {
        return ResponseEntity.ok(service.listarPorBoleta(idBoleta));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleBoleta> obtener(@PathVariable("id") Long id) {
        return service.obtener(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) {
        return service.eliminar(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<DetalleBoleta> actualizar(@PathVariable Long id, @Valid @RequestBody DetalleBoleta detalle) {
        DetalleBoleta actualizado = service.actualizar(id, detalle);
        return ResponseEntity.ok(actualizado);
    }

}

