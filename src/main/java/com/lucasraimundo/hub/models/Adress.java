package com.lucasraimundo.hub.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Adress {

	private String zipcode;
	private String street;
	private String district;
	private String city;
	private String state;
	
}
