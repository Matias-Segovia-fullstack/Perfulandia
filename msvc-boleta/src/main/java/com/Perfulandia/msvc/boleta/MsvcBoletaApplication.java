package com.Perfulandia.msvc.boleta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.Perfulandia.msvc.boleta.clients")
public class MsvcBoletaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcBoletaApplication.class, args);
	}

}
