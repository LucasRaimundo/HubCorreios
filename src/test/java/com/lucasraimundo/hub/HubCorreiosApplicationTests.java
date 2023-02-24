package com.lucasraimundo.hub;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.HttpStatusCode;
import org.mockserver.springtest.MockServerTest;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucasraimundo.hub.models.Adress;
import com.lucasraimundo.hub.service.CorreiosService;

@MockServerTest({"correios.base.url=http://localhost:${mockServerPort}/ceps.csv"})
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
class HubCorreiosApplicationTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private CorreiosService service;
	
	private MockServerClient mockServer;

	@Test
	@Order(1)
	public void testGetZipcodeWhenNotReady() throws Exception{
		mockMvc.perform(get("/zipcode/03358150")).andExpect(status().isServiceUnavailable());
	}
	
	
	
	@Test
	@Order(2)
	public void testSetupOK() throws Exception {
		String csvContent = "SP,Sao Paulo,Vila Formosa,3358150,Rua Ituri,,,,,,,,,,";  
		
		mockServer.when(request()
				.withMethod("GET")
		.withPath("/ceps.csv"))
			.respond(response()
					.withStatusCode(HttpStatusCode.OK_200.code())
					.withContentType(org.mockserver.model.MediaType.PLAIN_TEXT_UTF_8)
					.withBody(csvContent)
					);
		
		service.setup();
	}
	
	
	@Test
	@Order(3)
	public void testGetZipcodeThatDoesntExist() throws Exception {
		mockMvc.perform(get("/zipcode/22222222")).andExpect(status().isNoContent());
	}
	
	@Test
	@Order(4)
	public void testGetZipcodeOk() throws Exception {
		MvcResult result = mockMvc.perform(get("/zipcode/03358150"))
				.andExpect(status().isOk())
				.andReturn();
		String resultStr = result.getResponse().getContentAsString();
		
		String adressToCompareStr = new ObjectMapper().writeValueAsString(
				Adress.builder().city("Sao Paulo").district("Vila Formosa").state("SP").street("Rua Ituri").zipcode("03358150").build());
		
		JSONAssert.assertEquals(adressToCompareStr, resultStr, false);
	}

}
