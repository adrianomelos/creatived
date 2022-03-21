package com.cd.api.app.resources;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cd.api.app.dto.JwtRequest;
import com.cd.api.app.dto.JwtResponse;
import com.cd.api.app.entity.User;
import com.cd.api.app.exceptions.RequisicaoInvalidaException;
import com.cd.api.app.repository.UserRepository;
import com.cd.api.app.security.JWTUtil;

@RestController
@CrossOrigin
@RequestMapping(value = "/auth")
public class AuthJwt {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JWTUtil jwtTokenUtil;

	@Autowired
	private UserRepository userRepository;

	
	@PostMapping
	public JwtResponse createAuthenticationUser(@RequestBody JwtRequest authenticationRequest) throws Exception {

		Optional<User> user = userRepository.findByUsername(authenticationRequest.getEmail());
		if (!user.isPresent()) {
			throw new RequisicaoInvalidaException("Usuario não encontrado");
		}

		authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

		String token = jwtTokenUtil.generateToken(user.get().getUsername());
		return new JwtResponse(token);
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new RequisicaoInvalidaException("Senha inválida");
		}
	}
}