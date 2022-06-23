package com.springboot.tfg.app.models.entity;

import java.util.Date;
import java.util.Objects;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "alquila_detalles")
public class AlquilaDetalles {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_alquila_detalles;
	
	private String hora_inicio;
	
	private String hora_final;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha;
	
	private Long tarjeta;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_pista_aux")
	private Pistas pista;

	public AlquilaDetalles() {}
	
	public Long getId_alquila_detalles() {
		return id_alquila_detalles;
	}

	public void setId_alquila_detalles(Long id_alquila_detalles) {
		this.id_alquila_detalles = id_alquila_detalles;
	}

	public String getHora_inicio() {
		return hora_inicio;
	}

	public void setHora_inicio(String hora_inicio) {
		this.hora_inicio = hora_inicio;
	}

	public String getHora_final() {
		return hora_final;
	}

	public void setHora_final(String hora_final) {
		this.hora_final = hora_final;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Long getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(Long tarjeta) {
		this.tarjeta = tarjeta;
	}

	public Pistas getPista() {
		return pista;
	}

	public void setPista(Pistas pista) {
		this.pista = pista;
	}
}
