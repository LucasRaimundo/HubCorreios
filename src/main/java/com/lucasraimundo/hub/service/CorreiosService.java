package com.lucasraimundo.hub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasraimundo.hub.exception.NoContentException;
import com.lucasraimundo.hub.models.Adress;
import com.lucasraimundo.hub.models.Status;
import com.lucasraimundo.hub.repository.AdressRepository;
import com.lucasraimundo.hub.repository.AdressStatusRepository;

@Service
public class CorreiosService {
	
	@Autowired
	private AdressRepository adressRepository;
	
	@Autowired
	private AdressStatusRepository adressStatusRepository;

	public Status getStatus() {
		return Status.READY;
	}
	
	public Adress getAdressByZipcode(String zipcode) throws NoContentException {
		return adressRepository.findById(zipcode).orElseThrow(NoContentException::new);
	}
	
	public void setup() {
		
	}
}
