package com.jewelry;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class MvcConfigTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	void 遷移のテスト() throws Exception {
		transitionTest("/login", "login");
	}

	void transitionTest(String pass, String viewName) throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(pass))
			.andExpect(MockMvcResultMatchers.status()
				.isOk())
			.andExpect(MockMvcResultMatchers.view()
				.name(viewName));
	}

}
