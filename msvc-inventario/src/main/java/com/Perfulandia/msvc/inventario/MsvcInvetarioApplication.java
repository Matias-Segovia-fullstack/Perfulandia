package com.Perfulandia.msvc.inventario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.Perfulandia.msvc.inventario.clients")
public class MsvcInvetarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcInvetarioApplication.class, args);
	}

}
