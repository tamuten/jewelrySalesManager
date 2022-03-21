package com.jewelry.domain.repository;

import java.sql.Date;

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
import com.jewelry.constant.Const;
import com.jewelry.dataset.CsvDataSetLoader;
import com.jewelry.domain.model.Customer;
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
public class CustomerRepositoryTest {
	@Autowired
	private CustomerRepository repository;

	@Test
	@Order(1)
	@DatabaseSetup("/testdata/CustomerRepositoryTest/init-data")
	@ExpectedDatabase(value = "/testdata/CustomerRepositoryTest/after-create-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	void 顧客作成のテスト_DBチェック() {
		Shozoku newShozoku = Shozoku.builder()
				.id(1)
				.build();
			Tantosha newTantosha = Tantosha.builder()
				.id(1)
				.name("田中一郎")
				.shozoku(newShozoku)
				.role(Const.ROLE_ADMIN)
				.build();
		Customer newCustomer = Customer.builder()
				.name("山田太郎")
				.nameKana("ヤマダタロウ")
				.birthday(Date.valueOf("2022-3-21"))
				.gender("male")
				.bloodType("A")
				.address("埼玉県上尾市瓦葺1111-1-101")
				.memo("鮮度の高い品物がお好きです。")
				.tantosha(newTantosha)
				.signupDate(Date.valueOf("2022-3-21"))
				.build();
		repository.create(newCustomer);
	}
}
