package com.springboot.tfg.app.models.entity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class TarjetaClases {

	@Pattern(regexp = "5[1-5][0-9]{14}$", message = "Tiene que tener el formato de Mastercard.")
	private String numero;
	
	@NotEmpty
	private String nombre;

	private int dia;
	
	private int año;

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public int getAño() {
		return año;
	}

	public void setAño(int año) {
		this.año = año;
	}
}
