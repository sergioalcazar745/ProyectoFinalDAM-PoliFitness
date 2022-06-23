package com.springboot.tfg.app.models.service;

import java.util.List;

import com.springboot.tfg.app.models.entity.Pistas;

public interface IPistasService {

	public Pistas findByTipo(String nombre);

	public List<Pistas> findAll();
}
