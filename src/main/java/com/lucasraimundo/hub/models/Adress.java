package com.lucasraimundo.hub.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Adress {

	@Id
	private String zipcode;
	private String street;
	private String district;
	private String state;
	private String city;
	
}
