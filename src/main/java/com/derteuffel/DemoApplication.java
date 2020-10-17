package com.derteuffel;

import com.derteuffel.entities.ERole;
import com.derteuffel.entities.Role;
import com.derteuffel.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class DemoApplication{

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	/*@Override
	public void run(String... args) throws Exception {

		Role expert = new Role(ERole.ROLE_EXPERT);
		Role coordonateur = new Role(ERole.ROLE_COORDONATEUR);
		Role user = new Role(ERole.ROLE_USER);
		Role root = new Role(ERole.ROLE_ROOT);
		Role secretaire = new Role(ERole.ROLE_SECRETAIRE);
		roleRepository.save(expert);
		roleRepository.save(coordonateur);
		roleRepository.save(user);
		roleRepository.save(root);
		roleRepository.save(secretaire);


	}*/
}
