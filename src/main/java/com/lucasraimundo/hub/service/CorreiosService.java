package com.lucasraimundo.hub.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.lucasraimundo.hub.HubCorreiosApplication;
import com.lucasraimundo.hub.exception.NoContentException;
import com.lucasraimundo.hub.exception.NotReadyException;
import com.lucasraimundo.hub.models.Adress;
import com.lucasraimundo.hub.models.AdressStatus;
import com.lucasraimundo.hub.models.Status;
import com.lucasraimundo.hub.repository.AdressRepository;
import com.lucasraimundo.hub.repository.AdressStatusRepository;
import com.lucasraimundo.hub.repository.SetupRepository;

@Service
public class CorreiosService {
	
	private static Logger logger = LoggerFactory.getLogger(CorreiosService.class);

	@Autowired
	private AdressRepository adressRepository;

	@Autowired
	private AdressStatusRepository statusRepository;

	@Autowired
	private SetupRepository setupRepository;
	
	@Value("${setup.on.startup}")
	private boolean setupOnStartup;

	public Status getStatus() {
		return statusRepository.findById(AdressStatus.DEFAULT_ID)
				.orElse(AdressStatus.builder().status(Status.NEED_SETUP).build()).getStatus();
	}

	public Adress getAdressByZipcode(String zipcode) throws NoContentException, NotReadyException {
		if (!this.getStatus().equals(Status.READY))
			throw new NotReadyException();

		return adressRepository.findById(zipcode).orElseThrow(NoContentException::new);
	}

	private void saveStatus(Status status) {
		statusRepository.save(AdressStatus.builder().id(AdressStatus.DEFAULT_ID).status(status).build());
	}

	@EventListener(ApplicationStartedEvent.class)
	protected void setupOnStartUp() {
		
		if(!setupOnStartup) {
			return;
		}
		
		try {
			this.setup();
		} catch (Exception exc) {
			HubCorreiosApplication.close(999);
			
		}
	}

	public void setup() throws Exception {
		logger.info("--------------");
		logger.info("--------------");
		logger.info("--------------");
		logger.info("--------------SETUP RUNNING ");
		logger.info("--------------");
		logger.info("--------------");
		
		if (this.getStatus().equals(Status.NEED_SETUP)) {
			this.saveStatus(Status.SETUP_RUNNING);

			try {
				this.adressRepository.saveAll(this.setupRepository.getFromOrigin());
			} catch (Exception e) {
				this.saveStatus(Status.NEED_SETUP);
				throw e;
			}
			this.saveStatus(Status.READY);
		}
		
		logger.info("--------------");
		logger.info("--------------");
		logger.info("--------------SERVICE READY");
	}
}
