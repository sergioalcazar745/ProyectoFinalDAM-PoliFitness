package com.springboot.tfg.app.models.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.springboot.tfg.app.models.entity.Cliente;

public interface IClienteDAO extends CrudRepository<Cliente, Long>{	
	
	public Cliente findByEmail(String email);

	@Override
	default Optional<Cliente> findById(Long id) {
		return null;
	}
	
}
