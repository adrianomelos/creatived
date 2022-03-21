package com.cd.api.app.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cd.api.app.entity.Roles;
import com.cd.api.app.entity.User;
import com.cd.api.app.repository.RolesRepository;
import com.cd.api.app.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RolesRepository rolesRepository;
	
	@Autowired
	private BCryptPasswordEncoder be;


	@Override
	public void run(String... args) throws Exception {

		List<User> listUser = userRepository.findAll();
		
		List<Roles> listRoles = rolesRepository.findAll();

		if (listRoles.size() < 2) {

			Roles user = new Roles();
			user.setName("USER");
			user.setId(null);

			Roles admin = new Roles();
			admin.setName("ADMIN");
			admin.setId(null);

			List<Roles> list = new ArrayList<>();

			list.add(user);
			list.add(admin);

			rolesRepository.saveAll(list);
		}
		
		List<Roles> user = rolesRepository.findByName("USER");
		List<Roles> admin = rolesRepository.findByName("ADMIN");
		

		if (listUser.size() == 0) {
			
			User maria = new User(null, "Adriano Melo", "adrianoshout@gmail.com", be.encode("123456"), "Rua dois 585", "79 1545-8585", user);
			User alex = new User(null, "Alex Green", "alex@gmail.com", be.encode("123456"), "Rua dois 585", "79 88885-9875", admin );
			User bob = new User(null, "Bob Grey", "bob@gmail.com", be.encode("123456"), "Rua Jornalista 458", "79 99952-3639", user);
			User marcia = new User(null, "Marcia Santos", "marcia@gmail.com", be.encode("123456"), "Av. Laudelino freire 585", "79 99952-9988", user);
			User tiago = new User(null, "Thiago Leite", "thiago@gmail.com", be.encode("123456"), "Rua dois 585", "79 99952-3355", admin );
			User daniel = new User(null, "Daniel Lima", "daniel@gmail.com", be.encode("123456"), "Rua são francisco", "79 99585-1122", user);

			userRepository.saveAll(Arrays.asList(maria, alex, bob, marcia, tiago, daniel));
		}

	}

}
