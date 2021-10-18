package com.example.restservice;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class TicketControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testSubjectMissingSuccess() throws Exception {
		this.mockMvc.perform(get("/search?attribute=subject&value=Missing"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].type").value("incident"))
				.andExpect(jsonPath("$[0].subject").value("Cargo Missing"))
				.andExpect(jsonPath("$[0].description").value("Nostrud ad sit velit cupidatat laboris ipsum nisi amet laboris ex exercitation amet et proident. Ipsum fugiat aute dolore tempor nostrud velit ipsum."))
				.andExpect(jsonPath("$[0].priority").value("high"))
				.andExpect(jsonPath("$[0].status").value("pending"));
	}

	@Test
	public void testMissingInWrongAttribute() throws Exception {
		this.mockMvc.perform(get("/search?attribute=type&value=Missing"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	public void testPriorityHighSuccess() throws Exception {
		this.mockMvc.perform(get("/search?attribute=priority&value=high"))
				.andDo(print())
				.andExpect(status().isOk())

				.andExpect(jsonPath("$[0].type").value("incident"))
				.andExpect(jsonPath("$[0].subject").value("Payment Sent Error"))
				.andExpect(jsonPath("$[0].description").value("Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit"))
				.andExpect(jsonPath("$[0].priority").value("high"))
				.andExpect(jsonPath("$[0].status").value("pending"))

				.andExpect(jsonPath("$[1].type").value("incident"))
				.andExpect(jsonPath("$[1].subject").value("Cargo Missing"))
				.andExpect(jsonPath("$[1].description").value("Nostrud ad sit velit cupidatat laboris ipsum nisi amet laboris ex exercitation amet et proident. Ipsum fugiat aute dolore tempor nostrud velit ipsum."))
				.andExpect(jsonPath("$[1].priority").value("high"))
				.andExpect(jsonPath("$[1].status").value("pending"));

	}

	@Test
	public void testHighInWrongAttribute() throws Exception {
		this.mockMvc.perform(get("/search?attribute=type&value=high"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
	}
	
	@Test
	public void testMissingAttribute() throws Exception {
		this.mockMvc.perform(get("/search?attribute=&value=high"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testMissingValue() throws Exception {
		this.mockMvc.perform(get("/search?attribute=type&value="))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testNonExistingAttribute() throws Exception {
		this.mockMvc.perform(get("/search?attribute=test&value=high"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testNonExistingValue() throws Exception {
		this.mockMvc.perform(get("/search?attribute=type&value=test"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
	}

}
