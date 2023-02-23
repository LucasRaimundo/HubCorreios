package com.lucasraimundo.hub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lucasraimundo.hub.exception.NoContentException;
import com.lucasraimundo.hub.models.Adress;
import com.lucasraimundo.hub.service.CorreiosService;

@RestController
public class CorreiosController {
	
	@Autowired
	private CorreiosService service;

	@GetMapping("/status")
	public String getStatus() {
		return "Service status: " + service.getStatus();
	}
	
	@GetMapping("/zipcode/{zipcode}")
	public Adress getAdressByZipcode(@PathVariable("zipcode") String zipcode) throws NoContentException {
		
		return this.service.getAdressByZipcode(zipcode);
		
		
	}
}
