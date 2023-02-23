package com.lucasraimundo.hub.service;

import org.springframework.stereotype.Service;

import com.lucasraimundo.hub.models.Adress;
import com.lucasraimundo.hub.models.Status;

@Service
public class CorreiosService {

	public Status getStatus() {
		return Status.READY;
	}
	
	public Adress getAdressByZipcode(String zipcode) {
		return null;
	}
	
	public void setup() {
		
	}
}
