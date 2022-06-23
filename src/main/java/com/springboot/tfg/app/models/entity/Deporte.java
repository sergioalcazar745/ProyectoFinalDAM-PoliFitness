package com.springboot.tfg.app.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="deporte")
public class Deporte implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_deporte;
	
	private String nombre;
	
	private String dias;
	
	private String hora;
	
	private int aforo;
	
	private int apuntados;
	
	private double precio;
	
	@ManyToMany(mappedBy = "deportes")
    private List<Cliente> clientes;
	
	public Deporte() {
	}
	
	public Deporte(Long id_deporte, String nombre, String dias, String hora, int aforo, int apuntados, double precio) {
		this.id_deporte = id_deporte;
		this.nombre = nombre;
		this.dias = dias;
		this.hora = hora;
		this.aforo = aforo;
		this.apuntados = apuntados;
		this.precio = precio;
	}

	public Long getId_deporte() {
		return id_deporte;
	}

	public void setId_deporte(Long id_deporte) {
		this.id_deporte = id_deporte;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDias() {
		return dias;
	}

	public void setDias(String dias) {
		this.dias = dias;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public int getAforo() {
		return aforo;
	}

	public void setAforo(int aforo) {
		this.aforo = aforo;
	}

	public int getApuntados() {
		return apuntados;
	}

	public void setApuntados(int apuntados) {
		this.apuntados = apuntados;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public List<Cliente> getListaCliente() {
		return clientes;
	}

	public void setListaCliente(List<Cliente> listaCliente) {
		this.clientes = listaCliente;
	}	
}
