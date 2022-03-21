package com.cd.api.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cd.api.app.entity.Roles;

@Repository
public interface RolesRepository extends MongoRepository<Roles, String> {

	List<Roles> findByName(String string);

}
