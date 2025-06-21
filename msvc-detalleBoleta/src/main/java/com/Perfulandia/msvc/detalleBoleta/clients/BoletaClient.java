package com.Perfulandia.msvc.detalleBoleta.clients;

import com.Perfulandia.msvc.detalleBoleta.dto.BoletaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-boleta", url = "${msvc.boleta.url}")
public interface BoletaClient {

    @GetMapping("/api/v1/boletas/{id}")
    BoletaDTO obtenerBoleta(@PathVariable("id") Long id);
}

