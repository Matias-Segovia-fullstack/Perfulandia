package com.Perfulandia.msvc.detalleBoleta.clients;

import com.Perfulandia.msvc.detalleBoleta.dto.ProductoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-producto", url = "http://localhost:8002/api/productos")
public interface ProductoClient {

    @GetMapping("/{id}")
    ProductoDTO obtenerProducto(@PathVariable("id") Long id);
}