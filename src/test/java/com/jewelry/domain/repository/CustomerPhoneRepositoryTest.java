package com.jewelry.domain.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
import com.jewelry.domain.model.CustomerPhone;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class) // DBUnitでCSVファイルを使えるよう指定。
@TestExecutionListeners({
		DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
		TransactionDbUnitTestExecutionListener.class // @DatabaseSetupや＠ExpectedDatabaseなどを使えるように指定
})
@Transactional
public class CustomerPhoneRepositoryTest {
	@Autowired
	private CustomerPhoneRepository repository;

	@Test
	@Order(1)
	@DatabaseSetup("/testdata/CustomerPhoneRepositoryTest/init-data")
	@ExpectedDatabase(value = "/testdata/CustomerPhoneRepositoryTest/after-create-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	void サンプル登録() {
		CustomerPhone newPhone1 = CustomerPhone.builder()
			.customerId(1)
			.phoneNumber("08000000000")
			.memo("テスト1")
			.build();
		CustomerPhone newPhone2 = CustomerPhone.builder()
			.customerId(1)
			.phoneNumber("09000000000")
			.memo("テスト2")
			.build();
		List<CustomerPhone> createList = new ArrayList<CustomerPhone>();
		createList.add(newPhone1);
		createList.add(newPhone2);
		int createdCount = repository.createAll(createList);
		assertEquals(createdCount, 2);
	}
}
