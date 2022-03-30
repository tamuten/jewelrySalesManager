package com.jewelry.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import com.jewelry.Message;
import com.jewelry.configuration.WebConfig;
import com.jewelry.domain.model.Shozoku;
import com.jewelry.domain.service.MessageService;
import com.jewelry.domain.service.ShozokuService;
import com.jewelry.form.ShozokuForm;

@SpringBootTest(classes = WebConfig.class)
public class ShozokuControllerTest {
	private MockMvc mockMvc;
	@Mock
	private ShozokuService shozokuService;
	@Mock
	private MessageService messageService;
	@InjectMocks
	private ShozokuController controller;

	@BeforeEach
	void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
			.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
			.build();
	}

	@SuppressWarnings("unchecked")
	@Test
	void findPageで取得できる所属が10件以内の場合ページ0でリクエストを送ると正常に取得できる() throws Exception {
		List<Shozoku> expectedList = new ArrayList<>();
		expectedList.add(new Shozoku(1, "化粧品"));
		expectedList.add(new Shozoku(2, "婦人服"));
		expectedList.add(new Shozoku(3, "紳士服"));
		expectedList.add(new Shozoku(4, "生活用品"));
		expectedList.add(new Shozoku(5, "物産展"));
		expectedList.add(new Shozoku(6, "食料品"));
		expectedList.add(new Shozoku(7, "レストラン"));
		expectedList.add(new Shozoku(8, "宝飾品"));
		expectedList.add(new Shozoku(9, "スポーツ用品"));
		expectedList.add(new Shozoku(10, "文房具"));

		Pageable pageable = PageRequest.of(0, 10);
		Page<Shozoku> expectedPage = new PageImpl<Shozoku>(expectedList, pageable, 10);

		when(shozokuService.findPage(pageable))
			.thenReturn(expectedPage);

		MvcResult result = mockMvc.perform(get("/shozoku/list?page=0"))
			.andExpect(status().isOk())
			.andExpect(view().name("homeLayout"))
			.andExpect(model().attribute("contents", "contents/shozoku/shozokuList :: shozokuList_contents"))
			.andReturn();

		Map<String, Object> model = result.getModelAndView()
			.getModel();
		assertTrue(model.containsKey("page"));
		assertThat(model.get("page")).isEqualTo(expectedPage);

		assertTrue(model.containsKey("shozokuList"));
		assertNotNull(model.get("shozokuList"));
		assertTrue(model.get("shozokuList") instanceof List<?>);
		List<Shozoku> shozokuList = (List<Shozoku>) model.get("shozokuList");
		assertThat(shozokuList.size()).isEqualTo(expectedList.size());

		verify(shozokuService, times(1)).findPage(pageable);
	}

	@Test
	void 一件表示() throws Exception {
		when(shozokuService.findByPk(1)).thenReturn(new Shozoku(1, "食品"));

		mockMvc.perform(get("/shozoku/detail/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("homeLayout"))
			.andExpect(model().attribute("contents", "contents/shozoku/shozoku :: shozoku_contents"))
			.andExpect(model().attribute("shozokuForm", hasProperty("id", is(1))))
			.andExpect(model().attribute("shozokuForm", hasProperty("name", is("食品"))));
	}

	@Test
	void 更新_バリデーションエラー() throws Exception {
		// 空文字
		ShozokuForm form = new ShozokuForm();
		form.setId(1);
		form.setName("");

		MvcResult result = mockMvc.perform(post("/shozoku/update").flashAttr("shozokuForm", form))
			.andExpect(model().hasErrors())
			.andExpect(model().attribute("shozokuForm", form))
			.andExpect(view().name("homeLayout"))
			.andExpect(model().attribute("contents", "contents/shozoku/shozoku :: shozoku_contents"))
			.andExpect(model().attribute("displayMode", "update"))
			.andReturn();

		BindingResult bindingResult = (BindingResult) result.getModelAndView()
			.getModel()
			.get(BindingResult.MODEL_KEY_PREFIX + "shozokuForm");
		String mes = bindingResult.getFieldError()
			.getCode();
		System.out.println(mes);
	}

	@Disabled // TODO:
	@Test
	void 更新_正常() throws Exception {
		// TODO:
		when(messageService.getMessage(Message.UPDATE)).thenReturn("更新が完了しました。");

		ShozokuForm form = new ShozokuForm();
		form.setId(1);
		form.setName("更新");

		mockMvc.perform(post("/shozoku/update").flashAttr("shozokuForm", form)
			.with(SecurityMockMvcRequestPostProcessors.csrf()));
		// .andExpect(model().hasNoErrors())
		// .andExpect(model().attribute("shozokuForm", form))
		// .andExpect(view().name("homeLayout"))
		// .andExpect(model().attribute("contents", "contents/shozoku/shozoku ::
		// shozoku_contents"))
		// .andExpect(model().attribute("displayMode", "update"))
		// .andExpect(model().attribute("message", "更新が完了しました。"));

	}

	@Test
	void 登録画面表示のテスト() throws Exception {
		mockMvc.perform(get("/shozoku/signup"))
			.andExpect(status().isOk())
			.andExpect(view().name("homeLayout"))
			.andExpect(model().attribute("displayMode", "signup"))
			.andExpect(model().attribute("contents", "contents/shozoku/shozoku :: shozoku_contents"))
			.andExpect(model().attribute("shozokuForm", hasProperty("id", nullValue())))
			.andExpect(model().attribute("shozokuForm", hasProperty("name", nullValue())));
	}

	@Test
	void 登録_バリデーションエラー() {
		// TODO: 空文字
		// TODO: null
		// TODO: 文字数オーバー
	}

	@Test
	void 登録_正常() {
		// TODO:
	}

	@Test
	void 削除() {
		// TODO:
	}

}
