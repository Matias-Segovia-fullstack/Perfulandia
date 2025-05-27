package com.Perfulandia.msvc.boleta.clients;

import com.Perfulandia.msvc.boleta.dto.SucursalDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-sucursal", url = "${msvc.sucursal.url}")
public interface SucursalClient {

    @GetMapping("/api/v1/sucursales/{id}")
    SucursalDTO obtenerSucursal(@PathVariable("id") Long id);
}

