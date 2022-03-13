package com.jewelry.domain.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.jewelry.domain.model.Tantosha;

@SpringBootTest
@TestExecutionListeners({
		DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
		TransactionDbUnitTestExecutionListener.class // @DatabaseSetupや＠ExpectedDatabaseなどを使えるように指定
})
@Transactional
public class TantoshaServiceTest {
	@Autowired
	private TantoshaService tantoshaService;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	void createTest() {
		assertCreateTantosha("田中一郎", 4);
		assertCreateTantosha("ああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああ", 5);
		assertCreateFault("");
		assertCreateFault(null);
		assertCreateFault("あああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああ");
	}

	void assertCreateTantosha(String name, int id) {
		Tantosha newTantosha = Tantosha.builder()
			.name(name)
			.build();

		int createdCount = tantoshaService.create(newTantosha);
		assertEquals(1, createdCount);
		assertEquals(id, newTantosha.getId());

		Map<String, Object> createdTantosha = jdbcTemplate
			.queryForMap("SELECT name, delete_flg FROM tantosha WHERE id = " + String.valueOf(id));
		assertEquals(name, createdTantosha.get("name"));
		assertEquals(false, createdTantosha.get("delete_flg"));
	}

	void assertCreateFault(String name) {
		Tantosha newTantosha = Tantosha.builder()
			.name(name)
			.build();
		assertThrows(DataIntegrityViolationException.class, () -> tantoshaService.create(newTantosha));
	}

}
