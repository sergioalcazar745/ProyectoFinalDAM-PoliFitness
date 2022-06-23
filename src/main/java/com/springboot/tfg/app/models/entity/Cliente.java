package com.springboot.tfg.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="cliente")
public class Cliente implements Serializable{
		
	private static final long serialVersionUID = 4836439129317129251L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_cliente;

	@NotBlank
	private String nombre;
	
	@NotBlank
	private String apellidos;
	
	@Min(111111111)
	@Max(999999999)
	private int telefono;

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha_nac;
	
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	private String password;
	
	private String foto;
	
	@JoinTable(
	        name = "se_apunta",
	        joinColumns = @JoinColumn(name = "id_cliente_aux", nullable = false),
	        inverseJoinColumns = @JoinColumn(name="id_deporte_aux", nullable = false)
	    )
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Deporte> deportes;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente", cascade = CascadeType.ALL)
	private List<Alquila> alquila;
	
	public void addDeporte(Deporte deporte){
		
        if(this.deportes == null){
            this.deportes = new ArrayList<>();
        }
        
        this.deportes.add(deporte);
    }
	
	public void addAlquila(Alquila alquila){
		
        if(this.alquila == null){
            this.alquila = new ArrayList<>();
        }
        
        this.alquila.add(alquila);
    }
	
	public Cliente(){
		
	}	

	public Long getId() {
		return id_cliente;
	}
	
	public void setId(Long id) {
		this.id_cliente = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public Date getFecha_nac() {
		return fecha_nac;
	}

	public void setFecha_nac(Date fecha_nac) {
		this.fecha_nac = fecha_nac;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public List<Deporte> getDeportes() {
		return deportes;
	}

	public void setListaDeportes(List<Deporte> listaDeportes) {
		this.deportes = listaDeportes;
	}

	public List<Alquila> getAlquila() {
		return alquila;
	}

	public void setAlquila(List<Alquila> alquila) {
		this.alquila = alquila;
	}
}
