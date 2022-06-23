package com.springboot.tfg.app.models.service;

import java.util.List;

import com.springboot.tfg.app.models.entity.Deporte;

public interface IDeporteService {

	public Deporte findByNombre(String nombre);
	
	public List<Deporte> findAll();
}
