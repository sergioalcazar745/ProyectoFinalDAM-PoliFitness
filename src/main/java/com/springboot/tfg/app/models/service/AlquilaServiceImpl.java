package com.springboot.tfg.app.models.service;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.tfg.app.models.dao.IAlquilaDAO;
import com.springboot.tfg.app.models.dao.IAlquilaDetallesDAO;
import com.springboot.tfg.app.models.entity.Alquila;
import com.springboot.tfg.app.models.entity.AlquilaDetalles;
import com.springboot.tfg.app.models.entity.Cliente;
import com.springboot.tfg.app.models.entity.Pistas;

@Service
public class AlquilaServiceImpl implements IAlquilaService{
	
	@Autowired
	private IAlquilaDAO alquilaDao;
	
	@Autowired
	private IAlquilaDetallesDAO alquilaDetallesDao;

	@Override
	public List<Alquila> findAll() {
		
		List<Alquila> listaAlquila = alquilaDao.findAll();
		
		return listaAlquila;
	}
	
	@Override
	public void insertarAlquila(Alquila alquila) {
		alquilaDao.save(alquila);
		
	}

	@Override
	@Transactional
	public void eliminarAlquila(Alquila alquila) {
		ArrayList<AlquilaDetalles> listaDetalles = new ArrayList<AlquilaDetalles>();
		listaDetalles.add(alquila.getDetalles().get(0));
		
		alquilaDetallesDao.deleteInBatch(listaDetalles);	
		
		ArrayList<Alquila> listaAlquila = new ArrayList<Alquila>();
		listaAlquila.add(alquila);
		
		alquilaDao.deleteInBatch(listaAlquila);
	}
}
