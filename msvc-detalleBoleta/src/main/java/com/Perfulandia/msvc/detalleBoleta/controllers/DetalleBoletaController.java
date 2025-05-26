package com.Perfulandia.msvc.detalleBoleta.controllers;

import com.Perfulandia.msvc.detalleBoleta.models.entities.DetalleBoleta;
import com.Perfulandia.msvc.detalleBoleta.services.DetalleBoletaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/detalles")
public class DetalleBoletaController {
    private final DetalleBoletaService service;

    public DetalleBoletaController(DetalleBoletaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DetalleBoleta> crear(@RequestBody DetalleBoleta detalle) {
        DetalleBoleta guardado = service.guardar(detalle);
        return ResponseEntity.created(URI.create("/api/detalles/" + guardado.getId())).body(guardado);
    }

    @GetMapping("/boleta/{boletaId}")
    public ResponseEntity<List<DetalleBoleta>> listarPorBoleta(@PathVariable Long boletaId) {
        return ResponseEntity.ok(service.listarPorBoleta(boletaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleBoleta> obtener(@PathVariable Long id) {
        return service.obtener(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return service.eliminar(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
