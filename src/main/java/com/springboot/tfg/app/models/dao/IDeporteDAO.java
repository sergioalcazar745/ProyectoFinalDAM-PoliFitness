package com.springboot.tfg.app.models.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.springboot.tfg.app.models.entity.Deporte;

public interface IDeporteDAO extends CrudRepository<Deporte, Long>{
	
	public Deporte findByNombre(String nombre);
	
	public List<Deporte> findAll();
}
