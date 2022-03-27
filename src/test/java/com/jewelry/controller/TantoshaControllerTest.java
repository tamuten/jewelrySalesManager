package com.jewelry.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jewelry.constant.Const;
import com.jewelry.domain.model.Shozoku;
import com.jewelry.domain.model.Tantosha;
import com.jewelry.domain.service.TantoshaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
public class TantoshaControllerTest {
	private MockMvc mockMvc;

	@Mock
	private TantoshaService service;

	@InjectMocks
	private TantoshaController controller;

	private static final List<Tantosha> TANTOSHA_LIST_10 = 
		new ArrayList<>(){
			{
				add(new Tantosha(1, "佐藤一郎", new Shozoku(1, "化粧品"), Const.ROLE_ADMIN));
				add(new Tantosha(2, "佐藤次郎", new Shozoku(1, "化粧品"), Const.ROLE_GENERAL));
				add(new Tantosha(3, "佐藤三郎", new Shozoku(1, "化粧品"), Const.ROLE_GENERAL));
				add(new Tantosha(4, "佐藤四郎", new Shozoku(1, "化粧品"), Const.ROLE_GENERAL));
				add(new Tantosha(5, "佐藤葵", new Shozoku(1, "化粧品"), Const.ROLE_GENERAL));
				add(new Tantosha(6, "田中一郎", new Shozoku(1, "化粧品"), Const.ROLE_GENERAL));
				add(new Tantosha(7, "田中次郎", new Shozoku(1, "化粧品"), Const.ROLE_GENERAL));
				add(new Tantosha(8, "田中三郎", new Shozoku(1, "化粧品"), Const.ROLE_GENERAL));
				add(new Tantosha(9, "田中四郎", new Shozoku(1, "化粧品"), Const.ROLE_GENERAL));
				add(new Tantosha(10, "田中美咲", new Shozoku(1, "化粧品"), Const.ROLE_GENERAL));
				
			}
		};

	private static final Pageable PAGEABLE_10 = PageRequest.of(0, 10);
	
		private static final Page<Tantosha> TANTOSHA_PAGE_10 = 
			new PageImpl<Tantosha>(TANTOSHA_LIST_10, PAGEABLE_10, 10);

	@BeforeEach
	void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
				.build();
	}

	@Test
	@SuppressWarnings("unchecked")
	void getListTest() throws Exception {
		Mockito.when(service.findPage(PAGEABLE_10))
				.thenReturn(TANTOSHA_PAGE_10);

		MvcResult result = mockMvc.perform(get("/tantosha/list?page=0"))
				.andExpect(status().isOk())
				.andExpect(view().name("homeLayout"))
				.andExpect(model().attribute("contents", "contents/tantosha/tantoshaList :: tantoshaList_contents"))
				.andReturn();

		Map<String, Object> model = result.getModelAndView()
				.getModel();

				assertTrue(model.containsKey("page"));
				assertThat(model.get("page")).isEqualTo(TANTOSHA_PAGE_10);
		assertTrue(model.containsKey("tantoshaList"));
		assertNotNull(model.get("tantoshaList"));
		assertTrue(model.get("tantoshaList") instanceof List<?>);
		List<Tantosha> tantoshaList = (List<Tantosha>) model.get("tantoshaList");
	
		assertThat(tantoshaList.size()).isEqualTo(TANTOSHA_LIST_10.size());

		verify(service, times(1)).findPage(PAGEABLE_10);
	}

	@Test
	void 一件表示() {
		// TODO:
	}

	@Test
	void 更新_正常() {
		// TODO:
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
