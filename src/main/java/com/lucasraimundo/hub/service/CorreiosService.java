package com.lucasraimundo.hub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasraimundo.hub.exception.NoContentException;
import com.lucasraimundo.hub.exception.NotReadyException;
import com.lucasraimundo.hub.models.Adress;
import com.lucasraimundo.hub.models.AdressStatus;
import com.lucasraimundo.hub.models.Status;
import com.lucasraimundo.hub.repository.AdressRepository;
import com.lucasraimundo.hub.repository.AdressStatusRepository;

@Service
public class CorreiosService {
	
	@Autowired
	private AdressRepository adressRepository;
	
	@Autowired
	private AdressStatusRepository statusRepository;

	public Status getStatus() {
		return statusRepository.findById(AdressStatus.DEFAULT_ID)
				.orElse(AdressStatus.builder().status(Status.NEED_SETUP).build()).getStatus();
	}
	
	public Adress getAdressByZipcode(String zipcode) throws NoContentException, NotReadyException {
		if (!this.getStatus().equals(Status.READY))
			throw new NotReadyException();
		
		return adressRepository.findById(zipcode).orElseThrow(NoContentException::new);
	}
	
	public void setup() {
		
	}
}
