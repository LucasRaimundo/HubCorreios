package com.lucasraimundo.hub.models;

public enum Status {

	NEED_SETUP, //need to download csv to correios
	SETUP_RUNNING, // its dowload / salving in the database
	READY; // service is ok for to use
}
