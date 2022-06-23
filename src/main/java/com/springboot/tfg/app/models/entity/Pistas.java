package com.springboot.tfg.app.models.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="pista")
public class Pistas {	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_pista;
	
	private String tipo;

	public Long getId() {
		return id_pista;
	}

	public void setId(Long id) {
		this.id_pista = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String nombre) {
		this.tipo = nombre;
	}
}
