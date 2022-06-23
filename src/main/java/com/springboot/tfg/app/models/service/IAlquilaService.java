package com.springboot.tfg.app.models.service;

import java.util.List;

import com.springboot.tfg.app.models.entity.Alquila;

public interface IAlquilaService {
	
	public List<Alquila> findAll();
	
	public void insertarAlquila(Alquila alquila);
	
	public void eliminarAlquila(Alquila alquila);
}
