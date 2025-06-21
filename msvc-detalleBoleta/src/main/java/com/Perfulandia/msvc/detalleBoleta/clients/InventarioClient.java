package com.Perfulandia.msvc.detalleBoleta.clients;

import com.Perfulandia.msvc.detalleBoleta.dto.InventarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "msvc-inventario", url = "${msvc.inventario.url}")
public interface InventarioClient {

    // Buscar inventario por idSucursal e idProducto
    @GetMapping("/api/v1/inventarios/sucursal/{idSucursal}/producto/{idProducto}")
    InventarioDTO findByProductAndSucursal(
            @PathVariable("idSucursal") Long idSucursal,
            @PathVariable("idProducto") Long idProducto
    );
    // Actualizar stock (PUT)
    @PutMapping("/api/v1/inventarios/sucursal/{idSucursal}/producto/{idProducto}")
    InventarioDTO actualizarStock(
            @PathVariable("idSucursal") Long idSucursal,
            @PathVariable("idProducto") Long idProducto,
            @RequestBody Integer nuevoStock
    );

}




