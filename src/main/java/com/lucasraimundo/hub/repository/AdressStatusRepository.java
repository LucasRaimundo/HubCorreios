package com.lucasraimundo.hub.repository;

import org.springframework.data.repository.CrudRepository;

import com.lucasraimundo.hub.models.Adress;

public interface AdressStatusRepository extends CrudRepository<Adress, String> {

}
