package com.cd.api.app.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.cd.api.app.security.JWTAuthenticationFilter;
import com.cd.api.app.security.JWTAuthorizationFilter;
import com.cd.api.app.security.JWTUtil;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SegurancaConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTUtil jwtUtil;

	private static final String[] PUBLIC_MATCHERS = { "/auth", };

	private static final String[] PUBLIC_MATCHERS_GET = { "/user", };
	
	private static final String[] PUBLIC_MATCHERS_POST = { "/user", };
	
	private static final String[] PUBLIC_MATCHERS_PUT = { "/user", };
	
	private static final String[] PUBLIC_MATCHERS_DELETE = { "/user", };

	@Override
	protected void configure(HttpSecurity http) throws Exception {


		http.cors().and().csrf().disable();
		http.authorizeRequests()
				.antMatchers(PUBLIC_MATCHERS).permitAll()
				.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).hasAnyAuthority("ADMIN", "USER")
				.antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).hasAnyAuthority("ADMIN")
				.antMatchers(HttpMethod.PUT, PUBLIC_MATCHERS_PUT).hasAnyAuthority("ADMIN")
				.antMatchers(HttpMethod.DELETE, PUBLIC_MATCHERS_DELETE).hasAnyAuthority("ADMIN")
				.anyRequest().authenticated();
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
		configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
