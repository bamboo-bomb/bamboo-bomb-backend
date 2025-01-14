package com.bamboo.BambooBomb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class SuperBambooBombApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().load();

        System.setProperty("CLIENT_ID", dotenv.get("CLIENT_ID"));

		SpringApplication.run(SuperBambooBombApplication.class, args);
	}

}
