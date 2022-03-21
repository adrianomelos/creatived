package com.cd.api.app.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Roles implements Serializable, GrantedAuthority {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	private String name;

	@JsonIgnore
	@Override
	public String getAuthority() {
		return name;
	}
	
}
