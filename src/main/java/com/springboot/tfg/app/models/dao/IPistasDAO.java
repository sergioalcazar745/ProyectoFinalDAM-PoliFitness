package com.springboot.tfg.app.models.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.springboot.tfg.app.models.entity.Pistas;

public interface IPistasDAO extends CrudRepository<Pistas, Long>{

	public Pistas findByTipo(String nombre);
	
	public List<Pistas> findAll();
}
