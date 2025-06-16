package com.Perfulandia.msvc.inventario.clients;

import com.Perfulandia.msvc.inventario.dto.ProductoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-producto", url = "${msvc.producto.url}")
public interface ProductoClient {
    @GetMapping("/api/v1/productos/{id}")
    ProductoDTO obtenerProducto(@PathVariable("id") Long id);
}