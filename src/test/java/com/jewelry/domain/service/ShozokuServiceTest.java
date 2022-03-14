package com.jewelry.domain.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.jewelry.ForeignKey;
import com.jewelry.ForeignKeyDisableTestExecutionListener;
import com.jewelry.dataset.CsvDataSetLoader;
import com.jewelry.domain.model.Shozoku;

@SpringBootTest
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class) // DBUnitでCSVファイルを使えるよう指定。
@TestExecutionListeners({
		DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
		ForeignKeyDisableTestExecutionListener.class,
})
@Transactional
public class ShozokuServiceTest {
	@Autowired
	private ShozokuService shozokuService;

	@Test
	@ForeignKey
	@DatabaseSetup("/testdata/ShozokuServiceTest/init-data")
	@ExpectedDatabase(value = "/testdata/ShozokuServiceTest/after-create-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	void 所属作成のテスト() {
		Shozoku newShozoku = Shozoku.builder()
			.name("新規所属")
			.build();
		int createdCount = shozokuService.create(newShozoku);
		assertEquals(1, createdCount);
		assertEquals(7, newShozoku.getId());
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
