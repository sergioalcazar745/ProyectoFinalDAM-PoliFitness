package com.springboot.tfg.app.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.tfg.app.models.dao.IClienteDAO;
import com.springboot.tfg.app.models.dao.IDeporteDAO;
import com.springboot.tfg.app.models.entity.Alquila;
import com.springboot.tfg.app.models.entity.AlquilaDetalles;
import com.springboot.tfg.app.models.entity.Cliente;
import com.springboot.tfg.app.models.entity.Deporte;
import com.springboot.tfg.app.models.entity.Fecha;
import com.springboot.tfg.app.models.entity.Pistas;
import com.springboot.tfg.app.models.entity.Tarjeta;
import com.springboot.tfg.app.models.entity.TarjetaClases;
import com.springboot.tfg.app.models.service.IAlquilaService;
import com.springboot.tfg.app.models.service.IClienteService;
import com.springboot.tfg.app.models.service.IDeporteService;
import com.springboot.tfg.app.models.service.IPistasService;

@Controller
public class ClienteController {

	private String nuevaContraseña = null;
	private String contraseñaCliente = null;
	private String fotoCliente = null;
	private boolean apuntado = false;

	@Autowired
	private IClienteDAO clienteDao;

	@Autowired
	private IClienteService clienteService;

	@Autowired
	private IDeporteService deporteService;

	@Autowired
	IDeporteDAO deporteDao;

	@Autowired
	private IPistasService pistasService;

	@Autowired
	private IAlquilaService alquilaService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping({ "/", "/index" })
	public String index(Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth.isAuthenticated()) {
			Cliente cliente = clienteDao.findByEmail(auth.getName());
			model.addAttribute("cliente", cliente);
		}

		return "index";
	}

	@GetMapping("/registro")
	public String registro(Model model) {
		Cliente cliente = new Cliente();
		model.addAttribute("cliente", cliente);
		return "registro";
	}

	@PostMapping("/registro")
	public String indexAfterRegistro(@Valid Cliente cliente, BindingResult result, Model model,
			@RequestParam(name = "file") MultipartFile imagen,
			@RequestParam(value = "condiciones", required = false) String check, RedirectAttributes flash) {

		if (result.hasErrors()) {

			for (ObjectError error : result.getAllErrors()) {
				System.err.println("Error: " + error);
			}

			return "registro";

		} else if (check == null) {
			model.addAttribute("danger", "Debes aceptar los términos.");
			return "registro";
		}
		
		List<Cliente> listaCliente = (List<Cliente>) clienteDao.findAll();
		
		for (Cliente cliente1 : listaCliente) {
			if(cliente1.getEmail().equals(cliente.getEmail())) {
				model.addAttribute("danger", "El correo ya existe.");
				return "registro";
			}
		}

		if (!imagen.isEmpty()) {
			Path directorioRecursos = Paths.get("C:/Temp/upload");
			String rootPath = directorioRecursos.toFile().getAbsolutePath();
			try {
				byte[] bytes = imagen.getBytes();
				Path rutaCompleta = Paths.get(rootPath + "//" + imagen.getOriginalFilename());
				Files.write(rutaCompleta, bytes);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			model.addAttribute("danger", "La foto no puede estar vacia");
			return "registro";
		}

		cliente.setFoto(imagen.getOriginalFilename());
		cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
		clienteService.registrarCliente(cliente);
		flash.addFlashAttribute("success", cliente.getNombre() + ", te has registrado correctamente.");

		return "redirect:/index";
	}

	// @Secured("ROLE_ADMIN")
	@GetMapping("/clases")
	public String clases(Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Cliente cliente = clienteDao.findByEmail(auth.getName());
		model.addAttribute("cliente", cliente);

		List<Deporte> listaDeportes = deporteService.findAll();

		model.addAttribute("bodyPump", listaDeportes.get(0).getNombre());
		model.addAttribute("bodyCombat", listaDeportes.get(1).getNombre());
		model.addAttribute("cicloIndoor", listaDeportes.get(2).getNombre());
		model.addAttribute("crossFit", listaDeportes.get(3).getNombre());
		model.addAttribute("pilates", listaDeportes.get(4).getNombre());

		return "clases";
	}

	@GetMapping("/cuenta")
	public String cuenta(Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Cliente cliente = clienteDao.findByEmail(auth.getName());

		contraseñaCliente = cliente.getPassword();
		fotoCliente = cliente.getFoto();
		model.addAttribute("cliente", cliente);

		return "cuenta";
	}

	@PostMapping("/cuenta")
	public String postCuenta(@Valid Cliente cliente, BindingResult result, Model model,
			@RequestParam(name = "file") MultipartFile imagen, RedirectAttributes flash) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Cliente clienteSesion = clienteDao.findByEmail(auth.getName());

		if (result.hasErrors()) {
			System.out.println("Error: " + result.getFieldError());
			return "redirect:/cuenta";
		}

		List<Cliente> listaCliente = (List<Cliente>) clienteDao.findAll();
		
		for (Cliente cliente1 : listaCliente) {
			if(cliente1.getEmail().equals(cliente.getEmail())) {
				if(!cliente1.getId().equals(cliente.getId())) {
					flash.addFlashAttribute("danger", "El correo ya existe.");
					return "redirect:/cuenta";
				}				
			}
		}
		
		/*if (nuevaContraseña == null) {
			cliente.setPassword(contraseñaCliente);
		} else {
			cliente.setPassword(passwordEncoder.encode(nuevaContraseña));
		}*/

		if (!imagen.isEmpty()) {
			String sFichero = "C:/Temp/upload//" + fotoCliente;
			File fichero = new File(sFichero);

			if (fichero.exists()) {
				fichero.delete();
			}

			insertarFoto(imagen);
			cliente.setFoto(imagen.getOriginalFilename());
		} else {
			cliente.setFoto(fotoCliente);
		}

		clienteSesion.setId(cliente.getId());
		clienteSesion.setNombre(cliente.getNombre());
		clienteSesion.setApellidos(cliente.getApellidos());
		clienteSesion.setEmail(cliente.getEmail());
		clienteSesion.setPassword(clienteSesion.getPassword());
		clienteSesion.setFecha_nac(cliente.getFecha_nac());
		clienteSesion.setTelefono(cliente.getTelefono());
		clienteSesion.setFoto(cliente.getFoto());

		clienteService.registrarCliente(clienteSesion);
		flash.addFlashAttribute("success", "Datos acutalizados");
		return "redirect:/cuenta";
	}

	@GetMapping("/changePassword")
	public String cambiarContraseña(Model model) {
		return "change-form";
	}

	@PostMapping("/changePassword")
	public String prueba(@RequestParam String newPassword, @RequestParam String confirmPassword, Model model,
			RedirectAttributes flash) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Cliente cliente = clienteDao.findByEmail(auth.getName());

		if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
			model.addAttribute("error", "Los campos no pueden esta vacíos.");
			return "change-form";
		}

		if (!newPassword.equals(confirmPassword)) {
			model.addAttribute("error", "No coinciden las contraseñas.");
			return "change-form";
		}

		//nuevaContraseña = newPassword;
		cliente.setPassword(passwordEncoder.encode(newPassword));
		clienteService.registrarCliente(cliente);
		flash.addFlashAttribute("success", "Guarda cambios para cambiar la contraseña");
		return "correcto";
	}
	
	@GetMapping("/correcto")
	public String correcto() {
		return "correcto";
	}

	@GetMapping("/clases/{nombre}")
	public String apuntarse(@PathVariable(name = "nombre") String nombre, Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Cliente cliente = clienteDao.findByEmail(auth.getName());
		model.addAttribute("cliente", cliente);

		Deporte deporte = deporteService.findByNombre(nombre);
		model.addAttribute("deporte", deporte);

		if (deporte.getApuntados() == deporte.getAforo()) {
			model.addAttribute("warning", "Esta clase esta llena");
		}

		TarjetaClases tarjeta = new TarjetaClases();
		model.addAttribute("tarjetaClases", tarjeta);

		return "apuntarse";
	}

	@PostMapping("/inscripcion")
	public String inscripcion(@Valid TarjetaClases tarjetaClases, BindingResult result,
			@RequestParam(name = "nombreDeporte") String nombre, Model model, RedirectAttributes flash) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Cliente cliente = clienteDao.findByEmail(auth.getName());

		Deporte deporte = deporteService.findByNombre(nombre);

		if (result.hasErrors()) {
			model.addAttribute("cliente", cliente);
			model.addAttribute("deporte", deporte);
			System.out.println("Error: " + result.getAllErrors());
			return "apuntarse";
		}

		for (Deporte depor : cliente.getDeportes()) {
			if (depor.getId_deporte() == deporte.getId_deporte()) {
				model.addAttribute("cliente", cliente);
				model.addAttribute("deporte", deporte);
				model.addAttribute("warning", "Ya estas apuntado esta clase");
				return "apuntarse";
			}
		}

		if (tarjetaClases.getAño() == 2021) {
			if (tarjetaClases.getDia() < 6) {
				model.addAttribute("deporte", deporte);
				model.addAttribute("cliente", cliente);
				model.addAttribute("danger", "La tarjeta tiene una fecha inválida.");

				return "apuntarse";
			}
		}

		if (deporte.getApuntados() == deporte.getAforo()) {
			model.addAttribute("deporte", deporte);
			model.addAttribute("cliente", cliente);
			model.addAttribute("warning", "Esta clase esta llena");

			return "apuntarse";
		}

		deporte.setApuntados(deporte.getApuntados() + 1);
		deporteDao.save(deporte);

		cliente.addDeporte(deporte);

		clienteService.registrarCliente(cliente);

		flash.addFlashAttribute("success", "Te has inscrito en; " + deporte.getNombre());

		return "redirect:/";
	}

	@GetMapping("/alquilar")
	public String alquilar(Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Cliente cliente = clienteDao.findByEmail(auth.getName());
		model.addAttribute("cliente", cliente);

		List<Pistas> listaPistas = pistasService.findAll();

		model.addAttribute("futbol", listaPistas.get(0).getTipo());
		model.addAttribute("baloncesto", listaPistas.get(1).getTipo());
		model.addAttribute("padel", listaPistas.get(2).getTipo());

		return "alquilar";
	}

	@GetMapping("/alquilar/{nombre}")
	public String alquilar(@PathVariable(name = "nombre") String nombre, Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Cliente cliente = clienteDao.findByEmail(auth.getName());
		model.addAttribute("cliente", cliente);

		Pistas pista = pistasService.findByTipo(nombre);
		model.addAttribute("pista", pista);

		Tarjeta tarjeta = new Tarjeta();
		model.addAttribute("tarjeta", tarjeta);
		
		List<AlquilaDetalles> listaDetalles = new ArrayList<>();
		model.addAttribute("alquiladas", listaDetalles);

		return "alquilados";
	}

	@PostMapping("/alquilado")
	public String alquilarPista(@Valid Tarjeta tarjeta, BindingResult result,
			@RequestParam(name = "tipo") String nombre, @RequestParam(name = "precio") String precio, Model model, RedirectAttributes flash) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Cliente cliente = clienteDao.findByEmail(auth.getName());
		Pistas pista = pistasService.findByTipo(nombre);

		if (result.hasErrors()) {

			model.addAttribute("cliente", cliente);
			model.addAttribute("pista", pista);

			return "alquilados";
		}

		String[] array = tarjeta.getHora().split(":");
		int hora = Integer.parseInt(array[0]) + tarjeta.getHoras();
		String horaFinal = hora + ":" + array[1];

		if (tarjeta.getAño() == 2021) {
			if (tarjeta.getDia() < 6) {

				model.addAttribute("pista", pista);
				model.addAttribute("cliente", cliente);
				List<AlquilaDetalles> listaDetalles = new ArrayList<>();
				model.addAttribute("alquiladas", listaDetalles);
				model.addAttribute("danger", "La tarjeta tiene una fecha inválida.");

				return "alquilados";
			}
		}

		for (int i = 0; i<cliente.getAlquila().size(); i++) {

			for(int j = 0; j<cliente.getAlquila().get(i).getDetalles().size(); j++) {
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String fecha = sdf.format(cliente.getAlquila().get(i).getDetalles().get(j).getFecha());
				Date fechaIntroducida = null;
				try {
					fechaIntroducida = sdf.parse(fecha);
				} catch (ParseException e) {
					e.printStackTrace();
				}
	
				if (cliente.getAlquila().get(i).getDetalles().get(j).getPista().getId() == pista.getId() && tarjeta.getFecha().equals(fechaIntroducida)) {
					model.addAttribute("pista", pista);
					model.addAttribute("cliente", cliente);
					List<AlquilaDetalles> listaDetalles = new ArrayList<>();
					model.addAttribute("alquiladas", listaDetalles);
					model.addAttribute("warning", "Ya has alquilado esta pista el " + cliente.getAlquila().get(i).getDetalles().get(j).getFecha().toString().substring(0, 11) + "a las " + cliente.getAlquila().get(i).getDetalles().get(j).getHora_inicio());
	
					return "alquilados";
				}
			}

		}

		List<Alquila> listaAlquila = alquilaService.findAll();

		for (int i = 0; i<listaAlquila.size(); i++) {

			for(int j = 0; j<listaAlquila.get(i).getDetalles().size(); j++) {

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String fecha = sdf.format(listaAlquila.get(i).getDetalles().get(j).getFecha());
				Date fechaArray = null;
				try {
					fechaArray = sdf.parse(fecha);
				} catch (ParseException e) {
					e.printStackTrace();
				}
	
				String[] arrayInicio = listaAlquila.get(i).getDetalles().get(j).getHora_inicio().split(":");
				String[] arrayFinal = listaAlquila.get(i).getDetalles().get(j).getHora_final().split(":");
				
				System.out.println("Holaaaaaaaaaaa");
				
				System.out.println(Integer.parseInt(array[0]));
				System.out.println(Integer.parseInt(arrayInicio[0]));
				System.out.println(hora);
				System.out.println(Integer.parseInt(arrayFinal[0]));
				System.out.println("");
				
				if (tarjeta.getFecha().equals(fechaArray)){				
					if((Integer.parseInt(array[0]) > Integer.parseInt(arrayInicio[0]) && Integer.parseInt(array[0]) < Integer.parseInt(arrayFinal[0]) ||
						hora > Integer.parseInt(arrayInicio[0]) && hora < Integer.parseInt(arrayFinal[0])) || 
						(Integer.parseInt(array[0]) <= Integer.parseInt(arrayInicio[0]) && hora >= Integer.parseInt(arrayFinal[0]))) {
						
						model.addAttribute("pista", pista);
						model.addAttribute("cliente", cliente);
						model.addAttribute("warning", "No esta disponible esta pista.");
						
						List<Alquila> listaDetallesEntera = alquilaService.findAll();
						List<AlquilaDetalles> listaDetalles = new ArrayList<>();
						
						for (Alquila al : listaDetallesEntera) {
							for (AlquilaDetalles alDetalle : al.getDetalles()) {
								
								SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
								String fecha1 = sdf1.format(alDetalle.getFecha());
								Date fechaArray1 = null;
								try {
									fechaArray1 = sdf.parse(fecha1);
								} catch (ParseException e) {
									e.printStackTrace();
								}
								
								if(tarjeta.getFecha().equals(fechaArray1)) {
									listaDetalles.add(alDetalle);
								}
							}
						}
						
						model.addAttribute("alquiladas", listaDetalles);
						
						System.out.println("ALQUILADAS: " + listaDetalles.size());
	
						return "alquilados";
					}
				}
	
				/*if (tarjeta.getHoras() == 2) {
					
					System.out.println(Integer.parseInt(array[0]));
					System.out.println("Inicio" + Integer.parseInt(arrayInicio[0]));
					System.out.println(hora);
					System.out.println("Final" + Integer.parseInt(arrayFinal[0]));	
					System.out.println();
						
					if(Integer.parseInt(array[0]) > Integer.parseInt(arrayInicio[0]) && Integer.parseInt(array[0]) < Integer.parseInt(arrayFinal[0]) ||
						hora > Integer.parseInt(arrayInicio[0]) && hora < Integer.parseInt(arrayFinal[0])) {
						System.out.println("Hola Sergio");
					}
					
					if (tarjeta.getFecha().equals(fechaArray)
							&& (Integer.parseInt(arrayHora[0]) == Integer.parseInt(array[0]) + 1
									|| Integer.parseInt(arrayHora[0]) == Integer.parseInt(array[0]) + 2)
							&& listaAlquila.get(i).getDetalles().get(j).getPista().getId() == pista.getId()) {
						
						System.out.println("Hola 2");
	
						model.addAttribute("pista", pista);
						model.addAttribute("cliente", cliente);
						model.addAttribute("warning", "No esta disponible esta pista.");
						
						List<Alquila> listaDetallesEntera = alquilaService.findAll();
						List<AlquilaDetalles> listaDetalles = new ArrayList<>();
						
						for (Alquila al : listaDetallesEntera) {
							for (AlquilaDetalles alDetalle : al.getDetalles()) {
								
								SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
								String fecha1 = sdf1.format(alDetalle.getFecha());
								System.out.println("FEcha1: " + fecha1);
								Date fechaArray1 = null;
								try {
									fechaArray1 = sdf1.parse(fecha1);
								} catch (ParseException e) {
									e.printStackTrace();
								}
								
								if(tarjeta.getFecha().equals(fechaArray1)) {
									System.out.println("IGUAAAAAAAAAAAAAAAAAAAL");
									listaDetalles.add(alDetalle);
								}
							}
						}
						
						System.out.println("ALQUILADAS: " + listaDetalles.size());
						
						model.addAttribute("alquiladas", listaDetalles);
	
						return "alquilados";
					}
	
				} else if (tarjeta.getHoras() == 3) {
					
					System.out.println("HORAS: " + tarjeta.getHoras());
	
					if (tarjeta.getFecha().equals(fechaArray)
							&& (Integer.parseInt(arrayInicio[0]) == Integer.parseInt(array[0]) + 1
									|| Integer.parseInt(arrayInicio[0]) == Integer.parseInt(array[0]) + 2
									|| Integer.parseInt(arrayInicio[0]) == Integer.parseInt(array[0]) + 3)
							&& listaAlquila.get(i).getDetalles().get(j).getPista().getId() == pista.getId()) {

						model.addAttribute("pista", pista);
						model.addAttribute("cliente", cliente);
						model.addAttribute("warning", "No esta disponible esta pista.");
						
						List<Alquila> listaDetallesEntera = alquilaService.findAll();
						List<AlquilaDetalles> listaDetalles = new ArrayList<>();
						
						for (Alquila al : listaDetallesEntera) {
							for (AlquilaDetalles alDetalle : al.getDetalles()) {
								
								SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
								String fecha1 = sdf1.format(alDetalle.getFecha());
								Date fechaArray1 = null;
								try {
									fechaArray1 = sdf.parse(fecha1);
								} catch (ParseException e) {
									e.printStackTrace();
								}
								
								if(tarjeta.getFecha().equals(fechaArray1)) {
									//alDetalle.setFecha(alDetalle.getFecha().toString().substring(alDetalle.getFecha().toString().indexOf("")));
									System.out.println("IGUAAAAAAAAAAAAAAAAAAAL");
									listaDetalles.add(alDetalle);
								}
							}
						}
						
						model.addAttribute("alquiladas", listaDetalles);
						
						System.out.println("ALQUILADAS: " + listaDetalles.size());
	
						return "alquilados";
					}
	
				} else if (tarjeta.getHoras() == 4) {
					
					System.out.println("HORAS: " + tarjeta.getHoras());
					
					if (tarjeta.getFecha().equals(fechaArray)
							&& (Integer.parseInt(arrayInicio[0]) == Integer.parseInt(array[0]) + 1
									|| Integer.parseInt(arrayInicio[0]) == Integer.parseInt(array[0]) + 2
									|| Integer.parseInt(arrayInicio[0]) == Integer.parseInt(array[0]) + 3
									|| Integer.parseInt(arrayInicio[0]) == Integer.parseInt(array[0]) + 4)
							&& listaAlquila.get(i).getDetalles().get(j).getPista().getId() == pista.getId()) {
	
						model.addAttribute("pista", pista);
						model.addAttribute("cliente", cliente);
						model.addAttribute("warning", "No esta disponible esta pista.");
						
						List<Alquila> listaDetallesEntera = alquilaService.findAll();
						List<AlquilaDetalles> listaDetalles = new ArrayList<>();
						
						for (Alquila al : listaDetallesEntera) {
							for (AlquilaDetalles alDetalle : al.getDetalles()) {
								
								SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
								String fecha1 = sdf1.format(alDetalle.getFecha());
								Date fechaArray1 = null;
								try {
									fechaArray1 = sdf.parse(fecha1);
								} catch (ParseException e) {
									e.printStackTrace();
								}
								
								if(tarjeta.getFecha().equals(fechaArray1)) {
									//alDetalle.setFecha(alDetalle.getFecha().toString().substring(alDetalle.getFecha().toString().indexOf("")));
									listaDetalles.add(alDetalle);
								}
							}
						}
						
						model.addAttribute("alquiladas", listaDetalles);
	
						return "alquilados";
					}
	
				} else if (tarjeta.getHoras() == 5) {
					
					System.out.println("HORAS: " + tarjeta.getHoras());
					
					if (tarjeta.getFecha().equals(fechaArray)
							&& (Integer.parseInt(arrayInicio[0]) == Integer.parseInt(array[0]) + 1
									|| Integer.parseInt(arrayInicio[0]) == Integer.parseInt(array[0]) + 2
									|| Integer.parseInt(arrayInicio[0]) == Integer.parseInt(array[0]) + 3
									|| Integer.parseInt(arrayInicio[0]) == Integer.parseInt(array[0]) + 4
									|| Integer.parseInt(arrayInicio[0]) == Integer.parseInt(array[0]) + 5)
							&& listaAlquila.get(i).getDetalles().get(j).getPista().getId() == pista.getId()) {
	
						model.addAttribute("pista", pista);
						model.addAttribute("cliente", cliente);
						model.addAttribute("warning", "No esta disponible esta pista.");
						
						List<Alquila> listaDetallesEntera = alquilaService.findAll();
						List<AlquilaDetalles> listaDetalles = new ArrayList<>();
						
						for (Alquila al : listaDetallesEntera) {
							for (AlquilaDetalles alDetalle : al.getDetalles()) {
								
								SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
								String fecha1 = sdf1.format(alDetalle.getFecha());
								Date fechaArray1 = null;
								try {
									fechaArray1 = sdf.parse(fecha1);
								} catch (ParseException e) {
									e.printStackTrace();
								}
								
								if(tarjeta.getFecha().equals(fechaArray1)) {
									//alDetalle.setFecha(alDetalle.getFecha().toString().substring(alDetalle.getFecha().toString().indexOf("")));
									System.out.println(alDetalle.getFecha().toString().substring(0,11));
									listaDetalles.add(alDetalle);
								}
							}
						}
						
						model.addAttribute("alquiladas", listaDetalles);
						
						System.out.println("ALQUILADAS: " + listaDetalles.size());
	
						return "alquilados";
					}
				}*/
			}
		}

		double precioFinal = Double.parseDouble(precio);
		
		Alquila alquila = new Alquila();
		alquila.setCliente(cliente);
		alquila.setPrecio(precioFinal);		
		
		AlquilaDetalles detalles = new AlquilaDetalles();
		detalles.setFecha(tarjeta.getFecha());
		detalles.setHora_inicio(tarjeta.getHora());
		detalles.setHora_final(horaFinal);
		detalles.setTarjeta(Long.parseLong(tarjeta.getNumero()));
		detalles.setPista(pista);
		
		alquila.addDetalles(detalles);	
		
		alquilaService.insertarAlquila(alquila);

		flash.addFlashAttribute("success", "Has alquilado el campo de" + pista.getTipo());

		return "redirect:/";
	}

	@GetMapping("/mis-productos")
	public String misProductos(Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Cliente cliente = clienteDao.findByEmail(auth.getName());
		model.addAttribute("cliente", cliente);

		return "mis_productos";
	}

	@GetMapping("/mi-producto/{nombre}/{fecha}")
	public String misProductosPDF(@PathVariable(name = "nombre") String nombre, @PathVariable(name = "fecha") String fecha, Model model) {

		double precio = 0.0;
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Cliente cliente = clienteDao.findByEmail(auth.getName());
		model.addAttribute("cliente", cliente);

		if(fecha.toString().equals("nada")) {
			Deporte deporte = deporteService.findByNombre(nombre);
			model.addAttribute("deporte", deporte);
		}else {
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date fechaArray = null;
			try {
				fechaArray = sdf.parse(fecha);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			AlquilaDetalles alquila = null;
			
			for (int i = 0; i<cliente.getAlquila().size(); i++) {
				for(AlquilaDetalles alDetalle : cliente.getAlquila().get(i).getDetalles()) {
					if(alDetalle.getFecha().toString().equals(fecha) && alDetalle.getPista().getTipo().equals(nombre)) {
						alquila = alDetalle;
						precio = cliente.getAlquila().get(i).getPrecio();
					}
				}
			}
			
			model.addAttribute("alquila", alquila);
			model.addAttribute("precio", precio);
			model.addAttribute("nombrePista", nombre);
		}

		model.addAttribute("fecha", fecha);

		return "mis_productos";
	}

	@PostMapping("/desapuntarme")
	public String desapuntarme(@RequestParam(name = "nombreDeporte") String nombre, Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Cliente cliente = clienteDao.findByEmail(auth.getName());		

		for (int i = 0; i < cliente.getDeportes().size(); i++) {
			if (cliente.getDeportes().get(i).getNombre().equals(nombre)) {
				cliente.getDeportes().remove(i);
			}
		}

		Deporte deporte = deporteService.findByNombre(nombre);
		deporte.setApuntados(deporte.getApuntados() - 1);

		clienteDao.save(cliente);

		model.addAttribute("cliente", cliente);

		model.addAttribute("success", "Te has desapuntado de: " + nombre.toUpperCase());
		return "mis_productos";
	}
	
	@PostMapping("/cancelar")
	public String cancelar(@RequestParam(name = "id") String id, Model model, RedirectAttributes flash) {

		Alquila lo = null;
		Long idLong = null;
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Cliente cliente = clienteDao.findByEmail(auth.getName());
		
		Date fechaactual = new Date();
		
		for (int i = 0; i<cliente.getAlquila().size(); i++) {
			for(AlquilaDetalles alDetalle : cliente.getAlquila().get(i).getDetalles()) {
				if (cliente.getAlquila().get(i).getId() == Integer.parseInt(id)) {
					if(fechaactual.after(alDetalle.getFecha())) {
						flash.addFlashAttribute("success", "No se puede cancelar por que la fecha ya ha pasado");
						return "redirect:/mis-productos";
					}else {
						lo = cliente.getAlquila().get(i);
					}
				}
			}
		}

		/*for (int i = 0; i < cliente.getAlquila().size(); i++) {			
			if (cliente.getAlquila().get(i).getId() == Integer.parseInt(id)) {
				lo = cliente.getAlquila().get(i);
				idLong = cliente.getAlquila().get(i).getId();
			}
		}*/

		alquilaService.eliminarAlquila(lo);

		//model.addAttribute("cliente", cliente);

		flash.addFlashAttribute("success", "Has cancelado el alquiler");
		return "redirect:/mis-productos";
	}
	
	@RequestMapping(value = "/listaPista", method = RequestMethod.GET)
	public String listaPista(@RequestParam(name = "fecha") String fecha, Model model) {
		
		System.out.println("Cacatua: " + fecha);
		
		return "index";
	}

	public boolean insertarFoto(MultipartFile foto) {

		Path directorioRecursos = Paths.get("C:/Temp/upload");
		String rootPath = directorioRecursos.toFile().getAbsolutePath();
		try {
			byte[] bytes = foto.getBytes();
			Path rutaCompleta = Paths.get(rootPath + "//" + foto.getOriginalFilename());
			Files.write(rutaCompleta, bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}
}
