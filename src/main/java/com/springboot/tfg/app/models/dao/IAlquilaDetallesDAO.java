package com.springboot.tfg.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.springboot.tfg.app.models.entity.Alquila;
import com.springboot.tfg.app.models.entity.AlquilaDetalles;

public interface IAlquilaDetallesDAO extends CrudRepository<AlquilaDetalles, Long>, JpaRepository<AlquilaDetalles, Long>{
	
}
