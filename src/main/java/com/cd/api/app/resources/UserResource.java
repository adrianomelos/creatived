package com.cd.api.app.resources;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cd.api.app.entity.User;
import com.cd.api.app.service.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserResource {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public Page<User> findAll(
			@RequestParam(required = true, defaultValue = "10") int qtd,
			@RequestParam(required = true, defaultValue = "0") int page,
			@RequestParam(value = "nome", required = false) Optional<String> nome) {
		
		Pageable paginacao = PageRequest.of(page, qtd);
		
		return userService.findAll(paginacao, nome.orElse(null));
	}
	
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable String id) {
		userService.delete(id);
	}

	@PostMapping
	public User save(@RequestBody User user){
		return userService.save(user);
	}
	
	@GetMapping(value = "/{id}")
	public User findById(@PathVariable String id) {
		return userService.findById(id);
	}
	
	@PutMapping(value = "/{id}")
	public User update(@PathVariable String id, @RequestBody User user) {
		return userService.update(id, user);
	}

}
