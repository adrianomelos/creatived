package com.cd.api.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cd.api.app.entity.User;
import com.cd.api.app.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository repo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<User> cli = repo.findByUsername(email);
		
		if (cli == null) {
			throw new UsernameNotFoundException(email);
		}
		
		User c = new User();
		
		c.setEndereco(cli.get().getEndereco());
		c.setId(cli.get().getId());
		c.setNome(cli.get().getNome());
		c.setPassword(cli.get().getPassword());
		c.setPerfil(cli.get().getPerfil());
		c.setTelefone(cli.get().getTelefone());
		c.setUsername(cli.get().getUsername());
			
		return c;
	}
}