package com.cd.api.app.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cd.api.app.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	Optional<User> findByUsername(String email);

	Page<User> findByNomeRegex(String nome, Pageable paginacao);

}
