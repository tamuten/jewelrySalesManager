package com.jewelry.domain.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
import com.jewelry.domain.repository.jpa.ShozokuJpaRepository;

@SpringBootTest
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class) // DBUnitでCSVファイルを使えるよう指定。
@TestExecutionListeners({
		DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
		TransactionDbUnitTestExecutionListener.class // @DatabaseSetupや＠ExpectedDatabaseなどを使えるように指定
})
@Transactional
public class ShozokuJpaRepositoryTest {
	@Autowired
	ShozokuJpaRepository repository;

	@Test
	@DatabaseSetup("/testdata/ShozokuServiceTest/init-data")
	@ExpectedDatabase(value = "/testdata/ShozokuServiceTest/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	void findAllで全件取得する() {
		List<Shozoku> expected = new ArrayList<Shozoku>();
		expected.add(new Shozoku(1, "所属1"));
		expected.add(new Shozoku(2, "所属2"));
		expected.add(new Shozoku(3, "所属3"));
		expected.add(new Shozoku(4, "婦人服"));
		expected.add(new Shozoku(5, "家庭用品"));
		expected.add(new Shozoku(6, "食品"));

		List<Shozoku> actual = repository.findAll();
		assertThat(actual.size()).isEqualTo(expected.size());
		assertThat(actual.get(0)).isEqualTo(expected.get(0));
		assertThat(actual.get(1)).isEqualTo(expected.get(1));
		assertThat(actual.get(2)).isEqualTo(expected.get(2));
		assertThat(actual.get(3)).isEqualTo(expected.get(3));
		assertThat(actual.get(4)).isEqualTo(expected.get(4));
		assertThat(actual.get(5)).isEqualTo(expected.get(5));
	}
}
