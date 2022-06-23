package com.springboot.tfg.app.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
	
	@GetMapping("/login")
	public String login(@RequestParam(value="error", required=false) String error,
			@RequestParam(value="logout", required=false) String logout, Model model, Principal principal, RedirectAttributes flash) {
					
		if(principal != null) {
			flash.addFlashAttribute("info", "Ya has iniciado sesión anteriormente.");
			return "redirect:/";
		}
		
		if(error != null) {
			model.addAttribute("error", "El correo o la contraseña son incorrecto.");
		}
		
		if(logout != null) {
			flash.addFlashAttribute("success", "Has cerrado sesión con éxito.");
			return "redirect:/";
		}
		
		return "login";
	}
}
