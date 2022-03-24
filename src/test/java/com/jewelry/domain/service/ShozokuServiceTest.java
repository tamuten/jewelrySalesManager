package com.jewelry.domain.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.jewelry.ForeignKeyDisableTestExecutionListener;
import com.jewelry.constant.Const;
import com.jewelry.dataset.CsvDataSetLoader;
import com.jewelry.domain.model.Shozoku;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class) // DBUnitでCSVファイルを使えるよう指定。
@TestExecutionListeners({
		DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
		ForeignKeyDisableTestExecutionListener.class,
})
@Transactional
public class ShozokuServiceTest {
	@Autowired
	private ShozokuService shozokuService;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	@Order(1)
	@DatabaseSetup("/testdata/ShozokuServiceTest/init-data")
	@ExpectedDatabase(value = "/testdata/ShozokuServiceTest/after-create-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	void 所属作成のテスト_DBチェック() {
		Shozoku newShozoku = Shozoku.builder()
			.name("新規所属")
			.build();
		int createdCount = shozokuService.create(newShozoku);
		assertEquals(1, createdCount);
		assertEquals(7, newShozoku.getId());
	}

	@Test
	@Order(2)
	void 所属作成のテスト() {
		作成＿正常系のテスト("", 8);
		作成＿正常系のテスト(Const.CHARS_50, 9);

		作成_異常系のテスト(null);
		作成_異常系のテスト(Const.CHARS_51);
	}

	void 作成＿正常系のテスト(String name, int expectedId) {
		Shozoku newShozoku = Shozoku.builder()
			.name(name)
			.build();
		int createdCount = shozokuService.create(newShozoku);
		assertEquals(1, createdCount);
		assertEquals(expectedId, newShozoku.getId());
	}

	void 作成_異常系のテスト(String name) {
		Shozoku newShozoku = Shozoku.builder()
			.name(name)
			.build();
		assertThrows(DataIntegrityViolationException.class, () -> shozokuService.create(newShozoku));
	}

	@Test
	@Order(3)
	@DatabaseSetup("/testdata/ShozokuServiceTest/init-data")
	@ExpectedDatabase(value = "/testdata/ShozokuServiceTest/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	void 全件選択のテスト() {
		Shozoku expect1 = Shozoku.builder()
			.id(1)
			.name("所属1")
			.build();
		Shozoku expect2 = Shozoku.builder()
			.id(2)
			.name("所属2")
			.build();
		Shozoku expect3 = Shozoku.builder()
			.id(3)
			.name("所属3")
			.build();
		Shozoku expect4 = Shozoku.builder()
			.id(4)
			.name("婦人服")
			.build();
		Shozoku expect5 = Shozoku.builder()
			.id(5)
			.name("家庭用品")
			.build();
		Shozoku expect6 = Shozoku.builder()
			.id(6)
			.name("食品")
			.build();
		List<Shozoku> actual = shozokuService.findAll();
		log.info("actual shozokuList = {}", actual);
		assertEquals(6, actual.size());
		assertEquals(actual, Arrays.asList(expect1, expect2, expect3, expect4, expect5, expect6));
	}

	@Test
	@Order(4)
	@DatabaseSetup("/testdata/ShozokuServiceTest/init-data")
	@ExpectedDatabase(value = "/testdata/ShozokuServiceTest/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	void 一件選択のテスト() {
		Shozoku expect = Shozoku.builder()
			.id(1)
			.name("所属1")
			.build();
		Shozoku actual = shozokuService.findByPk(1);
		assertEquals(expect, actual);
	}

	@Test
	@Order(5)
	@DatabaseSetup("/testdata/ShozokuServiceTest/init-data")
	@ExpectedDatabase(value = "/testdata/ShozokuServiceTest/after-update-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	void 更新のテスト() {
		Shozoku updateShozoku = Shozoku.builder()
			.id(6)
			.name("更新後")
			.build();
		int updateCount = shozokuService.update(updateShozoku);
		assertEquals(updateCount, 1);
	}

	@Test
	@Order(6)
	@DatabaseSetup("/testdata/ShozokuServiceTest/init-data")
	@ExpectedDatabase(value = "/testdata/ShozokuServiceTest/after-delete-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	void 削除のテスト() {
		jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");
		int deleteCount = shozokuService.delete(6);
		assertEquals(deleteCount, 1);
		jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
	}
}
