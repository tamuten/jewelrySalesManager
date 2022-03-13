package com.jewelry.domain.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
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
	private static final String ROLE_ADMIN = "ROLE_ADMIN";
	private static final String ROLE_GENERAL = "ROLE_GENERAL";
	private static final String CHARS_50 = "あああああああああああああああああああああああああああ"
			+ "あああああああああああああああああああああああ";
	private static final String CHARS_51 = "あああああああああああああああああああああああああああ"
			+ "ああああああああああああああああああああああああ";
	@Autowired
	private TantoshaService tantoshaService;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	void 担当者作成のテスト() {

		// 正常系
		正常系のテスト("田中一郎", 5, ROLE_ADMIN, 4);// 通常値
		正常系のテスト(CHARS_50, 3, ROLE_GENERAL, 5);// 境界値
		正常系のテスト(CHARS_50, 3, CHARS_50, 5);// 境界値
		正常系のテスト("", 1, ROLE_GENERAL, 6);// 空文字
		正常系のテスト("", 1, "", 6);// 空文字

		// 異常系
		// null
		制約違反のテスト(null, 1, null);
		制約違反のテスト("", 1, null);
		制約違反のテスト(null, 1, "");
		// 文字数オーバー
		制約違反のテスト(CHARS_51, 1, "");
		制約違反のテスト(CHARS_51, 1, CHARS_51);
		制約違反のテスト("", 1, CHARS_51);

		存在しない所属IDのテスト("", 0, "");
		存在しない所属IDのテスト("", 10000, "");
	}

	void 正常系のテスト(String name, int shozoku_id, String role, int expectedId) {
		Tantosha newTantosha = Tantosha.builder()
			.name(name)
			//			.shozokuId(shozoku_id)
			.role(role)
			.build();

		int createdCount = tantoshaService.create(newTantosha);
		assertEquals(1, createdCount);
		assertEquals(expectedId, newTantosha.getId());

		Map<String, Object> createdTantosha = jdbcTemplate
			.queryForMap("SELECT name, shozoku_id, role FROM tantosha WHERE id = " + String.valueOf(expectedId));
		assertEquals(name, createdTantosha.get("name"));
		assertEquals(shozoku_id, createdTantosha.get("shozoku_id"));
		assertEquals(role, createdTantosha.get("role"));
	}

	void 制約違反のテスト(String name, int shozoku_id, String role) {
		Tantosha newTantosha = Tantosha.builder()
			.name(name)
			//			.shozokuId(shozoku_id)
			.role(role)
			.build();
		assertThrows(DataIntegrityViolationException.class, () -> tantoshaService.create(newTantosha));
	}

	void 存在しない所属IDのテスト(String name, int shozoku_id, String role) {
		Tantosha newTantosha = Tantosha.builder()
			.name(name)
			//			.shozokuId(shozoku_id)
			.role(role)
			.build();
		assertThrows(DataIntegrityViolationException.class, () -> tantoshaService.create(newTantosha));
	}

	@Test
	void findAllTest() {
		List<Tantosha> actualList = tantoshaService.findAll();
		assertEquals(3, actualList.size());
	}

	@Test
	void selectOneTest() {
		Tantosha actual = tantoshaService.findByPk(1);
		assertEquals(1, actual.getId());
		assertEquals("admin", actual.getName());
		assertEquals("ROLE_ADMIN", actual.getRole());
	}

	@Test
	void updateTest() {
		final String name = "update";
		final String role = "ROLE_ADMIN";
		Map<String, Object> tantosha2 = jdbcTemplate
			.queryForMap("SELECT name, role FROM tantosha WHERE id = " + String.valueOf(2));
		Tantosha newTantosha = Tantosha.builder()
			.id((Integer) tantosha2.get("id"))
			.name(name)
			.role(role)
			.build();
		int updateCount = tantoshaService.update(newTantosha);
		assertEquals(1, updateCount);

		Map<String, Object> actual = jdbcTemplate
			.queryForMap("SELECT name, role FROM tantosha WHERE id = " + String.valueOf(2));
		assertEquals(name, actual.get("name"));
		assertEquals(role, actual.get("role"));
	}

	@Test
	void deleteTest() {
		int targetId = 3;
		int deletedCount = tantoshaService.delete(targetId);
		assertEquals(deletedCount, 1);

		int count = jdbcTemplate
			.queryForObject("SELECT COUNT(*) FROM tantosha WHERE id = " + String.valueOf(targetId), Integer.class);
		assertEquals(count, 0);
	}
}
