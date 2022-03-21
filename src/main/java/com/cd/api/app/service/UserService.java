package com.cd.api.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cd.api.app.entity.User;
import com.cd.api.app.exceptions.RequisicaoInvalidaException;
import com.cd.api.app.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder be;

	public void delete(String id) {
		
		Optional<User> user = userRepository.findById(id);

		if (user.isEmpty()) {
			throw new RequisicaoInvalidaException("Usuário não encontrado!!");
		} else {
			userRepository.delete(user.get());
		}
	}

	public User save(User user) {
		
		user.setPassword(be.encode(user.getPassword()));
		
		return userRepository.save(user);
	}

	public Page<User> findAll(Pageable paginacao, String nome) {
		
		return userRepository.findByNomeRegex(nome, paginacao);
	}

	public User update(String id, User user) {
		
		Optional<User> u = userRepository.findById(id);
		
		if (u.isEmpty()) {
			throw new RequisicaoInvalidaException("Usuário não encontrado!!");
		} 

		u.get().setUsername(user.getUsername());
		u.get().setEndereco(user.getEndereco());
		u.get().setNome(user.getNome());
		u.get().setPassword(user.getPassword());
		u.get().setTelefone(user.getTelefone());

		return userRepository.save(u.get());

	}

	public User findById(String id) {
		
		Optional<User> user = userRepository.findById(id);
		
		if (user.isEmpty()) {
			throw new RequisicaoInvalidaException("Usuário não encontrado!!");
		}
		return user.get();
	}

}
