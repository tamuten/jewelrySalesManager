package com.jewelry.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.jewelry.domain.model.Tantosha;
import com.jewelry.domain.service.TantoshaService;

@SpringBootTest
public class TantoshaControllerTest {
	private MockMvc mockMvc;

	@Mock
	private TantoshaService service;

	@InjectMocks
	private TantoshaController controller;

	@BeforeEach
	void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
			.build();
	}

	@Test
	@SuppressWarnings("unchecked")
	void getListTest() throws Exception {
		Mockito.when(service.findAll())
			.thenReturn(Collections.emptyList());

		MvcResult result = mockMvc.perform(get("/tantosha/list"))
			.andExpect(status().isOk())
			.andExpect(view().name("homeLayout"))
			.andExpect(model().attribute("contents", "contents/tantosha/tantoshaList :: tantoshaList_contents"))
			.andReturn();

		Map<String, Object> model = result.getModelAndView()
			.getModel();
		assertTrue(model.containsKey("tantoshaList"));
		assertNotNull(model.get("tantoshaList"));
		assertTrue(model.get("tantoshaList") instanceof List<?>);
		List<Tantosha> tantoshaList = (List<Tantosha>) model.get("tantoshaList");
		assertTrue(tantoshaList.isEmpty());
	}
}
