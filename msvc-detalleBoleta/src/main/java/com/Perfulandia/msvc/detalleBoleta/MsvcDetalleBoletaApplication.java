package com.Perfulandia.msvc.detalleBoleta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.Perfulandia.msvc.detalleBoleta.clients")
@SpringBootApplication
public class MsvcDetalleBoletaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcDetalleBoletaApplication.class, args);
	}

}
