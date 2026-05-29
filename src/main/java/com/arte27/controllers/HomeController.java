package com.arte27.controllers;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
	
	@GetMapping("/")
	public String mostrarHome (Model model) {
		
		String camisa = "Camisa de Lino Premium";
		Date fechaRegistro = new Date();
		double precio = 25.0;
		boolean disponible = true;

		model.addAttribute("camisa", camisa); 
		model.addAttribute("fechaRegistro", fechaRegistro); 
		model.addAttribute("precio", precio); 
		model.addAttribute("disponible", disponible);
		
		return "home";
	}

}
