package com.jewelry.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.jewelry.domain.service.CustomerService;
import com.jewelry.domain.service.TantoshaService;
import com.jewelry.form.CustomerForm;

@SpringBootTest
public class CustomerControllerTest {
	MockMvc mockMvc;
	@Mock
	CustomerService customerService;
	@Mock
	TantoshaService tantoshaService;
	@InjectMocks
	CustomerController controller;

	private final CustomerForm EXPECTED = new CustomerForm();

	@BeforeEach
	void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
			.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
			.build();
	}

	@Test
	void detailは指定したIDで取得した顧客を取得する() throws Exception {
		MvcResult result = mockMvc.perform(get("/customer/detail/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("homeLayout"))
			.andExpect(model().attribute("contents", "contents/customer/customer :: customer_contents"))
			.andExpect(model().attribute("displayMode", "update"))
			.andReturn();

		Map<String, Object> model = result.getModelAndView()
			.getModel();
		assertTrue(model.containsKey("customerForm"));
		CustomerForm actual = (CustomerForm) model.get("customerForm");

	}
}
