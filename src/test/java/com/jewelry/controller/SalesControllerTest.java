package com.jewelry.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
public class SalesControllerTest {
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new SalesController())
			.build();
	}

	@Disabled
	@Test
	void getSalesTest() throws Exception {
		mockMvc.perform(get("/sales"))
			.andExpect(status().isOk())
			.andExpect(view().name("homeLayout"))
			.andExpect(model().attribute("contents", "contents/sales/sales :: sales_contents"));
		mockMvc.perform(get("/salesList"))
			.andExpect(status().isOk())
			.andExpect(view().name("homeLayout"))
			.andExpect(model().attribute("contents", "contents/sales/salesList :: salesList_contents"));
	}
}
