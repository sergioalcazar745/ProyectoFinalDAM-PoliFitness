package com.springboot.tfg.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.tfg.app.models.dao.IClienteDAO;
import com.springboot.tfg.app.models.entity.Cliente;
import com.springboot.tfg.app.models.service.MailService;

@Controller
public class MailController {
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private IClienteDAO clienteDao;
	
	@GetMapping("/cuenta/restablecerContraseña")
	public String restablecerContraseña(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Cliente cliente = clienteDao.findByEmail(auth.getName());
		
		model.addAttribute("cliente", cliente);
		return "restablecerContraseña";
	}
	
	@GetMapping("/cuenta/enviarMail/{email}")
	public String sendMail(@PathVariable(name="email") String email, RedirectAttributes flash) {
		
		String message = "Hemos recibido una petición para restablecer tu contraseña, por favor pincha en el siguiente enlace.\n" 
		+ "http://localhost:8181/changePassword";
		
		mailService.sendMail("polifitness.polideportivo@gmail.com", email, "Reestablecer contraseña", message);
		flash.addFlashAttribute("info", "Se ha enviado un correo para restablecer la contraseña");
		
		return "redirect:/";
	}
}
