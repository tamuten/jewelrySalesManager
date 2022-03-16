package com.jewelry.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import com.jewelry.domain.model.Shozoku;
import com.jewelry.domain.service.ShozokuService;
import com.jewelry.form.ShozokuForm;

@SpringBootTest
public class ShozokuControllerTest {
	private MockMvc mockMvc;
	@Mock
	private ShozokuService service;
	@InjectMocks
	private ShozokuController controller;

	@BeforeEach
	void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
			.build();
	}

	@SuppressWarnings("unchecked")
	@Test
	void 全件表示のテスト() throws Exception {
		Mockito.when(service.findAll())
			.thenReturn(List.of(new Shozoku(1, "食品")));

		MvcResult result = mockMvc.perform(get("/shozoku/list"))
			.andExpect(status().isOk())
			.andExpect(view().name("homeLayout"))
			.andExpect(model().attribute("contents", "contents/shozoku/shozokuList :: shozokuList_contents"))
			.andReturn();

		Map<String, Object> model = result.getModelAndView()
			.getModel();
		assertTrue(model.containsKey("shozokuList"));
		assertNotNull(model.get("shozokuList"));
		assertTrue(model.get("shozokuList") instanceof List<?>);
		List<Shozoku> shozokuList = (List<Shozoku>) model.get("shozokuList");
		assertEquals(shozokuList.get(0)
			.getId(), 1);
		assertEquals(shozokuList.get(0)
			.getName(), "食品");
	}

	@Test
	void 更新のテスト() throws Exception {
		ShozokuForm form = new ShozokuForm();
		form.setId(1);
		form.setName("");

		ResultActions results = mockMvc.perform(post("/shozoku/update").flashAttr("form", form))
			.andExpect(model().hasErrors())
			.andExpect(model().attribute("form", form))
			.andExpect(view().name("homeLayout"))
			.andExpect(model().attribute("contents", "contents/shozoku/shozoku :: shozoku_contents"))
			.andExpect(model().attribute("displayMode", "update"));

		BindingResult bindResult = (BindingResult) results.andReturn()
			.getModelAndView()
			.getModel()
			.get(BindingResult.MODEL_KEY_PREFIX + "form");
		String mes = bindResult.getFieldError()
			.getDefaultMessage();
		assertEquals("名前は1文字以上50文字以下で入力してください。", mes);
	}
}