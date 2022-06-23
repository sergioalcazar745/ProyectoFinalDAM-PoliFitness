package com.springboot.tfg.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.tfg.app.models.dao.IPistasDAO;
import com.springboot.tfg.app.models.entity.Pistas;

@Service
public class PistasServiceImpl implements IPistasService{

	@Autowired
	private IPistasDAO pistasDao;

	@Override
	public Pistas findByTipo(String nombre) {
		
		Pistas pista = pistasDao.findByTipo(nombre);
		
		return pista;
	}

	@Override
	public List<Pistas> findAll() {
		
		List<Pistas> listaPistas = pistasDao.findAll();
		
		return listaPistas;
	}	
}
