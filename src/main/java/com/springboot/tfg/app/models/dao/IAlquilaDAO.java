package com.springboot.tfg.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.springboot.tfg.app.models.entity.Alquila;
import com.springboot.tfg.app.models.entity.Deporte;

public interface IAlquilaDAO extends CrudRepository<Alquila, Long>, JpaRepository<Alquila, Long>{
	
	public List<Alquila> findAll();
}
