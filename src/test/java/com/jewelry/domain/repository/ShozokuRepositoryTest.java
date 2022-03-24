package com.jewelry.domain.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class) // DBUnitでCSVファイルを使えるよう指定。
@TestExecutionListeners({
		DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
		TransactionDbUnitTestExecutionListener.class // @DatabaseSetupや＠ExpectedDatabaseなどを使えるように指定
})
@Transactional
public class ShozokuRepositoryTest {
	@Autowired
	private ShozokuRepository repository;

	@Test
	@Order(1)
	@DatabaseSetup("/testdata/ShozokuRepositoryTest/findPage-init-data")
	@ExpectedDatabase(value = "/testdata/ShozokuRepositoryTest/findPage-init-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	void testFindPage() {
		List<Shozoku> expectedList = new ArrayList<>();
		expectedList.add(new Shozoku(11, "所属13"));
		expectedList.add(new Shozoku(12, "所属14"));
		expectedList.add(new Shozoku(13, "所属15"));
		expectedList.add(new Shozoku(14, "所属16"));
		expectedList.add(new Shozoku(15, "所属17"));
		expectedList.add(new Shozoku(16, "所属18"));
		expectedList.add(new Shozoku(17, "所属19"));
		expectedList.add(new Shozoku(18, "所属20"));
		expectedList.add(new Shozoku(19, "所属21"));
		expectedList.add(new Shozoku(20, "所属22"));

		Pageable pageable = PageRequest.of(1, 10);
		List<Shozoku> actualList = repository.findPage(pageable);
		assertThat(actualList.size()).isEqualTo(expectedList.size());
		assertThat(actualList).isEqualTo(expectedList);
	}
}
