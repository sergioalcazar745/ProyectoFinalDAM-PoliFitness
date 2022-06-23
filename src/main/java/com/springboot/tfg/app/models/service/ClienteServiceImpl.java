package com.springboot.tfg.app.models.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.tfg.app.models.dao.IAlquilaDAO;
import com.springboot.tfg.app.models.dao.IClienteDAO;
import com.springboot.tfg.app.models.entity.Alquila;
import com.springboot.tfg.app.models.entity.Cliente;

@Service
public class ClienteServiceImpl implements IClienteService, UserDetailsService{
	
	@Autowired
	private IClienteDAO clienteDao;
	
	@Autowired
	private IAlquilaDAO alquilaDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	private Logger logger = LoggerFactory.getLogger(ClienteServiceImpl.class);

	@Override
	public void registrarCliente(Cliente cliente) {
		clienteDao.save(cliente);
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Cliente cliente = clienteDao.findByEmail(username);
		
		if(cliente == null) {
        	logger.error("Error en el Login: no existe el usuario '" + username + "' en el sistema!");
        	throw new UsernameNotFoundException("Username: " + username + " no existe en el sistema!");
        }
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		/*for(Role role: cliente.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		}*/
		
		 /*if(authorities.isEmpty()) {
	        logger.error("Error en el Login: Usuario '" + username + "' no tiene roles asignados!");
	        throw new UsernameNotFoundException("Error en el Login: usuario '" + username + "' no tiene roles asignados!");
	     }*/
		
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		return new User(cliente.getEmail(), cliente.getPassword(), true, true, true, true, authorities);
	}

	@Override
	@Transactional
	public void deleteAlquila(Long id) {
		alquilaDao.deleteById(id);		
	}
}
