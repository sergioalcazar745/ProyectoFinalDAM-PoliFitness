package com.springboot.tfg.app;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ProyectoTfg1Application implements CommandLineRunner{
	
	/*@Autowired
	private BCryptPasswordEncoder passwordEncoder;*/

	public static void main(String[] args) {
		SpringApplication.run(ProyectoTfg1Application.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		/*String password = "lidia";
		
		for(int i = 0; i<1; i++) {
			String bcryptPassword = passwordEncoder.encode(password);
			System.out.println(bcryptPassword);
		}*/
	}

}
