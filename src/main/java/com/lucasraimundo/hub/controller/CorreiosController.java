package com.lucasraimundo.hub.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lucasraimundo.hub.models.Adress;

@RestController
public class CorreiosController {

	@GetMapping("/status")
	public String getStatus() {
		return "up";
	}
	
	@GetMapping("/zipcode/{zipcode}")
	public Adress getAdressByZipcode(@PathVariable("zipcode") String zipcode) {
		return Adress.builder().zipcode(zipcode).build();
	}
}
