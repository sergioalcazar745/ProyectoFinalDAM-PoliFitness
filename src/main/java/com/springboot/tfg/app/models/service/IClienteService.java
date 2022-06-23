package com.springboot.tfg.app.models.service;
import com.springboot.tfg.app.models.entity.Alquila;
import com.springboot.tfg.app.models.entity.Cliente;

public interface IClienteService {

	public void registrarCliente(Cliente cliente);
	
	public void deleteAlquila(Long id);
}
