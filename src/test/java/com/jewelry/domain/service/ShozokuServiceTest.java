package com.jewelry.domain.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.jewelry.domain.model.Shozoku;

@SpringBootTest
@TestExecutionListeners({
		DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
		TransactionDbUnitTestExecutionListener.class // @DatabaseSetupや＠ExpectedDatabaseなどを使えるように指定
})
@Transactional
public class ShozokuServiceTest {
	@Autowired
	private ShozokuService shozokuService;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	void 所属作成のテスト() {

	}

	void 作成＿正常系のテスト(String name, int expectedId) {
		Shozoku newShozoku = Shozoku.builder()
			.name(name)
			.build();
		int createdCount = shozokuService.create(newShozoku);
		assertEquals(1, createdCount);
		assertEquals(expectedId, newShozoku.getId());
	}
}
