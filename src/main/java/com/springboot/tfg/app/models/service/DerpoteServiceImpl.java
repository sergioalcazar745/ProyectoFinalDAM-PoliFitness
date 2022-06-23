package com.springboot.tfg.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.tfg.app.models.dao.IClienteDAO;
import com.springboot.tfg.app.models.dao.IDeporteDAO;
import com.springboot.tfg.app.models.entity.Deporte;

@Service
public class DerpoteServiceImpl implements IDeporteService{

	@Autowired
	private IDeporteDAO deporteDao;

	@Override
	public Deporte findByNombre(String nombre) {
		
		Deporte deporte = deporteDao.findByNombre(nombre);
		
		return deporte;
	}

	@Override
	public List<Deporte> findAll() {
		
		List<Deporte> listaDeportes = deporteDao.findAll();
		
		return listaDeportes;
	}
}
