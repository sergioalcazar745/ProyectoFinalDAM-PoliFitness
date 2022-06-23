package com.springboot.tfg.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "alquila")
public class Alquila implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private double precio;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cliente_aux")
	private Cliente cliente;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "id_alquila_aux")
	private List<AlquilaDetalles> detalles;
	
	public Alquila() {
		this.detalles = new ArrayList<AlquilaDetalles>();
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<AlquilaDetalles> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<AlquilaDetalles> detalles) {
		this.detalles = detalles;
	}
	
	public void addDetalles(AlquilaDetalles detalle) {
		this.detalles.add(detalle);
	}
}
