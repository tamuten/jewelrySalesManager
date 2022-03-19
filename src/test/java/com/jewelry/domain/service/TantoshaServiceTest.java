package com.jewelry.domain.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.jewelry.constant.Const;
import com.jewelry.dataset.CsvDataSetLoader;
import com.jewelry.domain.model.Shozoku;
import com.jewelry.domain.model.Tantosha;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class) // DBUnitでCSVファイルを使えるよう指定。
@TestExecutionListeners({
		DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
		TransactionDbUnitTestExecutionListener.class // @DatabaseSetupや＠ExpectedDatabaseなどを使えるように指定
})
@Transactional
public class TantoshaServiceTest {
	@Autowired
	private TantoshaService tantoshaService;

	@Test
	@Order(1)
	@DatabaseSetup("/testdata/TantoshaServiceTest/init-data")
	@ExpectedDatabase(value = "/testdata/TantoshaServiceTest/after-create-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	void 担当者作成のテスト_DBチェック() {
		Shozoku newShozoku = Shozoku.builder()
			.id(1)
			.build();
		Tantosha newTantosha = Tantosha.builder()
			.name("田中一郎")
			.shozoku(newShozoku)
			.role(Const.ROLE_ADMIN)
			.build();
		tantoshaService.create(newTantosha);
	}

	@DatabaseSetup("/testdata/TantoshaServiceTest/init-data")
	@Test
	@Order(2)
	void 担当者作成のテスト() {

		// 正常系
		正常系のテスト("田中一郎", 5, Const.ROLE_ADMIN, 5);// 通常値
		正常系のテスト(Const.CHARS_50, 3, Const.ROLE_GENERAL, 6);// 境界値
		正常系のテスト(Const.CHARS_50, 3, Const.CHARS_50, 7);// 境界値
		正常系のテスト("", 1, Const.ROLE_GENERAL, 8);// 空文字
		正常系のテスト("", 1, "", 9);// 空文字

		// 異常系
		// null
		制約違反のテスト(null, 1, null);
		制約違反のテスト("", 1, null);
		制約違反のテスト(null, 1, "");
		// 文字数オーバー
		制約違反のテスト(Const.CHARS_51, 1, "");
		制約違反のテスト(Const.CHARS_51, 1, Const.CHARS_51);
		制約違反のテスト("", 1, Const.CHARS_51);

		存在しない所属IDのテスト("", 0, "");
		存在しない所属IDのテスト("", 10000, "");
	}

	void 正常系のテスト(String name, int shozoku_id, String role, int expectedId) {
		Shozoku newShozoku = Shozoku.builder()
			.id(shozoku_id)
			.build();
		Tantosha newTantosha = Tantosha.builder()
			.name(name)
			.shozoku(newShozoku)
			.role(role)
			.build();

		int createdCount = tantoshaService.create(newTantosha);
		assertEquals(1, createdCount);
		assertEquals(expectedId, newTantosha.getId());

	}

	void 制約違反のテスト(String name, int shozoku_id, String role) {
		Shozoku newShozoku = Shozoku.builder()
			.id(shozoku_id)
			.build();
		Tantosha newTantosha = Tantosha.builder()
			.name(name)
			.shozoku(newShozoku)
			.role(role)
			.build();
		assertThrows(DataIntegrityViolationException.class, () -> tantoshaService.create(newTantosha));
	}

	void 存在しない所属IDのテスト(String name, int shozoku_id, String role) {
		Shozoku newShozoku = Shozoku.builder()
			.id(shozoku_id)
			.build();
		Tantosha newTantosha = Tantosha.builder()
			.name(name)
			.shozoku(newShozoku)
			.role(role)
			.build();
		assertThrows(DataIntegrityViolationException.class, () -> tantoshaService.create(newTantosha));
	}

	@Test
	@Order(3)
	@DatabaseSetup("/testdata/TantoshaServiceTest/init-data")
	@ExpectedDatabase(value = "/testdata/TantoshaServiceTest/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	void 全件選択のテスト() {
		List<Tantosha> actualList = tantoshaService.findAll();
		assertEquals(3, actualList.size());
	}

	@Test
	@Order(4)
	@DatabaseSetup("/testdata/TantoshaServiceTest/init-data")
	@ExpectedDatabase(value = "/testdata/TantoshaServiceTest/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	void 一件選択のテスト() {
		Tantosha actual = tantoshaService.findByPk(1);
		assertEquals(1, actual.getId());
		assertEquals("admin", actual.getName());
		assertEquals(1, actual.getShozoku().getId());
		assertEquals("所属1", actual.getShozoku().getName());
		assertEquals("ROLE_ADMIN", actual.getRole());
	}

	@Test
	@Order(5)
	@DatabaseSetup("/testdata/TantoshaServiceTest/init-data")
	@ExpectedDatabase(value = "/testdata/TantoshaServiceTest/after-update-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	void 更新のテスト() {
		Shozoku newShozoku = Shozoku.builder()
			.id(1)
			.build();
		Tantosha newTantosha = Tantosha.builder()
			.id(3)
			.name("更新後")
			.shozoku(newShozoku)
			.role(Const.ROLE_ADMIN)
			.build();
		int updateCount = tantoshaService.update(newTantosha);
		assertEquals(updateCount, 1);
	}

	@Test
	@Order(6)
	@DatabaseSetup("/testdata/TantoshaServiceTest/init-data")
	@ExpectedDatabase(value = "/testdata/TantoshaServiceTest/after-delete-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	void 削除のテスト() {
		int deletedCount = tantoshaService.delete(3);
		assertEquals(deletedCount, 1);
	}
}
