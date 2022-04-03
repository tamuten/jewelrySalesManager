package com.jewelry.domain.service;

import static com.jewelry.constant.Const.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
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
import com.jewelry.dataset.CsvDataSetLoader;
import com.jewelry.domain.model.Shozoku;
import com.jewelry.domain.model.Tantosha;

@SpringBootTest
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
	@DatabaseSetup("/testdata/TantoshaServiceTest/init-data")
	@ExpectedDatabase(value = "/testdata/TantoshaServiceTest/after-create-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	void 担当者作成のテスト_DBチェック() {
		Shozoku newShozoku = Shozoku.builder()
			.id(1)
			.build();
		Tantosha newTantosha = Tantosha.builder()
			.name("田中一郎")
			.shozoku(newShozoku)
			.role(ROLE_ADMIN)
			.build();
		tantoshaService.create(newTantosha);
	}

	@Test
	@DatabaseSetup("/testdata/TantoshaServiceTest/init-data")
	void 担当者作成のテスト() {

		// 正常系
		正常系のテスト("田中一郎", 5, ROLE_ADMIN);// 通常値
		正常系のテスト(CHARS_50, 3, ROLE_GENERAL);// 境界値
		正常系のテスト(CHARS_50, 3, CHARS_50);// 境界値
		正常系のテスト("", 1, ROLE_GENERAL);// 空文字
		正常系のテスト("", 1, "");// 空文字

		// 異常系
		// null
		制約違反のテスト(null, 1, null);
		制約違反のテスト("", 1, null);
		制約違反のテスト(null, 1, "");
		// 文字数オーバー
		制約違反のテスト(CHARS_51, 1, "");
		制約違反のテスト(CHARS_51, 1, CHARS_51);
		制約違反のテスト("", 1, CHARS_51);
	}

	void 正常系のテスト(String name, int shozoku_id, String role) {
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

	@Test
	@DatabaseSetup("/testdata/TantoshaServiceTest/init-data")
	@ExpectedDatabase(value = "/testdata/TantoshaServiceTest/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	void 全件選択のテスト() {
		List<Tantosha> actualList = tantoshaService.findAll();
		assertEquals(3, actualList.size());
	}

	@Test
	@DatabaseSetup("/testdata/TantoshaServiceTest/init-data")
	@ExpectedDatabase(value = "/testdata/TantoshaServiceTest/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	void 一件選択のテスト() {
		Tantosha actual = tantoshaService.findByPk(1);
		assertEquals(1, actual.getId());
		assertEquals("admin", actual.getName());
		assertEquals(1, actual.getShozoku()
			.getId());
		assertEquals("所属1", actual.getShozoku()
			.getName());
		assertEquals("ROLE_ADMIN", actual.getRole());
	}

	@Test
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
			.role(ROLE_ADMIN)
			.build();
		int updateCount = tantoshaService.update(newTantosha);
		assertEquals(updateCount, 1);
	}

	@Test
	@DatabaseSetup("/testdata/TantoshaServiceTest/init-data")
	@ExpectedDatabase(value = "/testdata/TantoshaServiceTest/after-delete-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	void 削除のテスト() {
		int deletedCount = tantoshaService.delete(3);
		assertEquals(deletedCount, 1);
	}

}
