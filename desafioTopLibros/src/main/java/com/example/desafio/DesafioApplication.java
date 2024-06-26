package com.example.desafio;

import com.example.desafio.principal.Principal;
import com.example.desafio.repository.AutorRepository;
import com.example.desafio.repository.LibroRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DesafioApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository libroRepository;
	@Autowired

	private AutorRepository autorRepository;

	public static void main(String[] args) {
		SpringApplication.run(DesafioApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(libroRepository, autorRepository);
		principal.muestraElMenu();
	}
}
