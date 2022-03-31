package com.jewelry.domain.repository;

import static com.jewelry.constant.Const.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.jewelry.dataset.CsvDataSetLoader;
import com.jewelry.domain.model.Customer;
import com.jewelry.domain.model.CustomerMail;
import com.jewelry.domain.model.CustomerPhone;
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
		Tantosha newTantosha = Tantosha.builder()
			.id(1)
			.build();
		Customer newCustomer = Customer.builder()
			.name("山本香織")
			.nameKana("ヤマモトカオリ")
			.birthday(Date.valueOf("2000-3-21"))
			.gender("female")
			.bloodType("A")
			.address("埼玉県上尾市瓦葺1111-1-101")
			.memo("新規追加")
			.tantosha(newTantosha)
			.signupDate(Date.valueOf("2022-3-21"))
			.build();
		repository.create(newCustomer);
	}

	@Test
	@Order(2)
	void 顧客作成のテスト() {
		// 正常系
		正常に登録(CHARS_50, CHARS_100, "2000-1-1", CHARS_10, CHARS_5, CHARS_100, CHARS_200, 2, "2000-1-1");
		正常に登録(CHARS_EMPTY, CHARS_EMPTY, CHARS_EMPTY, CHARS_EMPTY, CHARS_EMPTY, CHARS_EMPTY, CHARS_EMPTY, 2, "2000-1-1");
		正常に登録("山本美月", null, null, null, null, null, null, 3, "2000-1-1");
		正常に登録("山本美月", null, null, null, null, null, null, null, "2000-1-1");

		// 異常系
		登録に失敗(CHARS_51, CHARS_101, "2020-1-1", CHARS_11, CHARS_6, CHARS_101, CHARS_201, null, "2020-1-31");
		登録に失敗(CHARS_51, CHARS_100, "2020-1-1", CHARS_10, CHARS_5, CHARS_100, CHARS_200, null, "2020-1-31");
		登録に失敗(CHARS_50, CHARS_101, "2020-1-1", CHARS_10, CHARS_5, CHARS_100, CHARS_200, null, "2020-1-31");
		登録に失敗(CHARS_50, CHARS_100, "2020-1-1", CHARS_11, CHARS_5, CHARS_100, CHARS_200, null, "2020-1-31");
		登録に失敗(CHARS_50, CHARS_100, "2020-1-1", CHARS_10, CHARS_6, CHARS_100, CHARS_200, null, "2020-1-31");
		登録に失敗(CHARS_50, CHARS_100, "2020-1-1", CHARS_10, CHARS_5, CHARS_101, CHARS_200, null, "2020-1-31");
		登録に失敗(CHARS_50, CHARS_100, "2020-1-1", CHARS_10, CHARS_5, CHARS_100, CHARS_201, null, "2020-1-31");
	}

	/**
	 * createメソッドが正常に実行されるか検証します
	 *
	 * @param name 顧客名
	 * @param kana 顧客名カナ
	 * @param birthdayStr 生年月日（文字列）
	 * @param gender 性別
	 * @param bloodType 血液型
	 * @param address 住所
	 * @param memo メモ
	 * @param tantoshaId 担当者ID
	 * @param signupDateStr 登録日（文字列）
	 */
	void 正常に登録(String name, String kana, String birthdayStr, String gender, String bloodType, String address,
			String memo, Integer tantoshaId, String signupDateStr) {
		Date birthday = null;
		if (!(birthdayStr == null || birthdayStr.equals(""))) {
			birthday = Date.valueOf(birthdayStr);
		}

		Date signupDate = null;
		if (!(signupDateStr == null || signupDateStr.equals(""))) {
			signupDate = Date.valueOf(signupDateStr);
		}

		Tantosha newTantosha = Tantosha.builder()
			.id(tantoshaId)
			.build();
		Customer newCustomer = Customer.builder()
			.name(name)
			.nameKana(kana)
			.birthday(birthday)
			.gender(gender)
			.bloodType(bloodType)
			.address(address)
			.memo(memo)
			.tantosha(newTantosha)
			.signupDate(signupDate)
			.build();
		int createdCount = repository.create(newCustomer);
		assertEquals(createdCount, 1);
	}

	/**
	 * createメソッドが異常終了するか検証します
	 *
	 * @param name 顧客名
	 * @param kana 顧客名カナ
	 * @param birthdayStr 生年月日（文字列）
	 * @param gender 性別
	 * @param bloodType 血液型
	 * @param address 住所
	 * @param memo メモ
	 * @param tantoshaId 担当者ID
	 * @param signupDateStr 登録日（文字列）
	 */
	void 登録に失敗(String name, String kana, String birthdayStr, String gender, String bloodType, String address,
			String memo, Integer tantoshaId, String signupDateStr) {
		Date birthday = null;
		if (!(birthdayStr == null || birthdayStr.equals(""))) {
			birthday = Date.valueOf(birthdayStr);
		}

		Date signupDate = null;
		if (!(signupDateStr == null || signupDateStr.equals(""))) {
			signupDate = Date.valueOf(signupDateStr);
		}

		Tantosha newTantosha = Tantosha.builder()
			.id(tantoshaId)
			.build();
		Customer newCustomer = Customer.builder()
			.name(name)
			.nameKana(kana)
			.birthday(birthday)
			.gender(gender)
			.bloodType(bloodType)
			.address(address)
			.memo(memo)
			.tantosha(newTantosha)
			.signupDate(signupDate)
			.build();
		assertThrows(DataIntegrityViolationException.class, () -> repository.create(newCustomer));
	}

	@Test
	@Order(3)
	@DatabaseSetup("/testdata/CustomerRepositoryTest/findAll-init-data")
	@ExpectedDatabase(value = "/testdata/CustomerRepositoryTest/findAll-init-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	void 全件選択のテスト() {
		List<Customer> expectedList = new ArrayList<>();

		List<CustomerPhone> expectedPhones1 = new ArrayList<>();
		expectedPhones1.add(new CustomerPhone(1, 1, "07000000000", "佐藤一郎さんの電話番号１", false));
		expectedPhones1.add(new CustomerPhone(2, 1, "07000000001", "佐藤一郎さんの電話番号２", false));
		expectedPhones1.add(new CustomerPhone(3, 1, "07000000002", "佐藤一郎さんの電話番号３", false));
		List<CustomerMail> expectedMails1 = new ArrayList<>();
		expectedMails1.add(new CustomerMail(1, 1, "ichiro1@example.com", "佐藤一郎さんのメールアドレス１", false));
		expectedMails1.add(new CustomerMail(2, 1, "ichiro2@example.com", "佐藤一郎さんのメールアドレス２", false));
		expectedMails1.add(new CustomerMail(3, 1, "ichiro3@example.com", "佐藤一郎さんのメールアドレス３", false));
		expectedList.add(new Customer(
				1,
				"佐藤一郎",
				"サトウイチロウ",
				Date.valueOf("1993-2-20"),
				"male",
				"B",
				"埼玉県さいたま市北区〇〇町1-1-1",
				"サンプル",
				Date.valueOf("2022-3-22"),
				new Tantosha(1, "admin", new Shozoku(1, "所属1"), ROLE_ADMIN),
				expectedPhones1,
				expectedMails1));

		List<CustomerPhone> expectedPhones2 = new ArrayList<>();
		expectedPhones2.add(new CustomerPhone(4, 2, "08000000000", "佐藤次郎さんの電話番号１", false));
		List<CustomerMail> expectedMails2 = new ArrayList<>();
		expectedList.add(new Customer(
				2,
				"佐藤次郎",
				"サトウジロウ",
				Date.valueOf("1993-2-21"),
				"male",
				"A",
				"埼玉県さいたま市北区〇〇町1-1-1",
				"サンプル",
				Date.valueOf("2022-3-22"),
				new Tantosha(2, "user", new Shozoku(4, "婦人服"), ROLE_GENERAL),
				expectedPhones2,
				expectedMails2));

		List<CustomerPhone> expectedPhones3 = new ArrayList<>();
		expectedPhones3.add(new CustomerPhone(5, 3, "09000000000", "佐藤三郎さんの電話番号１", false));
		expectedPhones3.add(new CustomerPhone(6, 3, "09000000001", "佐藤三郎さんの電話番号２", false));
		List<CustomerMail> expectedMails3 = new ArrayList<>();
		expectedMails3.add(new CustomerMail(4, 3, "saburo1@example.com", "佐藤三郎さんのメールアドレス１", false));
		expectedMails3.add(new CustomerMail(5, 3, "saburo2@example.com", "佐藤三郎さんのメールアドレス２", false));
		expectedList.add(new Customer(
				3,
				"佐藤三郎",
				"サトウサブロウ",
				Date.valueOf("1993-2-22"),
				"male",
				"O",
				"埼玉県さいたま市北区〇〇町1-1-1",
				"サンプル",
				Date.valueOf("2022-3-22"),
				new Tantosha(3, "test", new Shozoku(6, "食品"), ROLE_GENERAL),
				expectedPhones3,
				expectedMails3));
		List<Customer> actualList = repository.findAll();
		assertEquals(3, actualList.size());
		assertEquals(expectedList, actualList);
	}

	@Test
	@Order(4)
	@DatabaseSetup("/testdata/CustomerRepositoryTest/init-data")
	@ExpectedDatabase(value = "/testdata/CustomerRepositoryTest/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	void 一件選択のテスト() {
		List<CustomerPhone> expectedPhones = new ArrayList<>();
		expectedPhones.add(new CustomerPhone(1, 1, "07000000000", "メモ", false));
		expectedPhones.add(new CustomerPhone(2, 1, "07000000001", "メモ", false));
		expectedPhones.add(new CustomerPhone(3, 1, "07000000002", "メモ", false));
		List<CustomerMail> expectedMails = new ArrayList<>();
		expectedMails.add(new CustomerMail(1, 1, "sample1@example.com", "メモ", false));
		expectedMails.add(new CustomerMail(2, 1, "sample2@example.com", "メモ", false));
		expectedMails.add(new CustomerMail(3, 1, "sample3@example.com", "メモ", false));
		Shozoku expectedShozoku = Shozoku.builder()
			.id(1)
			.name("所属1")
			.build();
		Tantosha expectedTantosha = Tantosha.builder()
			.id(1)
			.name("admin")
			.shozoku(expectedShozoku)
			.role(ROLE_ADMIN)
			.build();
		Customer expected = Customer.builder()
			.id(1)
			.name("佐藤一郎")
			.nameKana("サトウイチロウ")
			.birthday(Date.valueOf("1993-2-20"))
			.gender("male")
			.bloodType("B")
			.address("埼玉県さいたま市北区〇〇町1-1-1")
			.memo("サンプル")
			.tantosha(expectedTantosha)
			.signupDate(Date.valueOf("2022-3-22"))
			.customerPhoneList(expectedPhones)
			.customerMailList(expectedMails)
			.build();
		Customer actual = repository.findByPk(1);
		assertEquals(expected, actual);
	}

	@Test
	@Order(5)
	@DatabaseSetup("/testdata/CustomerRepositoryTest/init-data")
	@ExpectedDatabase(value = "/testdata/CustomerRepositoryTest/after-update-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	void ありうる全てのパラメータを更新しDBを検証() {
		Customer update = Customer.builder()
			.id(16)
			.name("田中葵")
			.nameKana("タナカアオイ")
			.birthday(Date.valueOf("1993-3-7"))
			.gender("female")
			.bloodType("B")
			.address("埼玉県さいたま市北区〇〇町1-2-3")
			.memo("サンプル変更")
			.tantosha(Tantosha.builder()
				.id(2)
				.build())
			.build();

		int updateCnt = repository.update(update);
		assertThat(updateCnt).isEqualTo(1);
	}

	@Test
	@Order(6)
	@DatabaseSetup("/testdata/CustomerRepositoryTest/init-data")
	@ExpectedDatabase(value = "/testdata/CustomerRepositoryTest/after-part-update-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	void 一部のパラメータを更新しDBを検証() {
		Customer update = Customer.builder()
			.id(16)
			.name("田中葵")
			.nameKana("タナカイチロウ")
			.birthday(Date.valueOf("1993-2-7"))
			.gender("male")
			.bloodType("AB")
			.address("埼玉県さいたま市北区〇〇町1-1-1")
			.memo("サンプル")
			.tantosha(Tantosha.builder()
				.id(1)
				.build())
			.build();

		int updateCnt = repository.update(update);
		assertThat(updateCnt).isEqualTo(1);
	}

	@Test
	@Order(7)
	@DatabaseSetup("/testdata/CustomerRepositoryTest/init-data")
	@ExpectedDatabase(value = "/testdata/CustomerRepositoryTest/after-delete-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	void 一件削除() {
		int deleteCnt = repository.delete(20);
		assertThat(deleteCnt).isEqualTo(1);
	}

	@Test
	@Order(8)
	@DatabaseSetup("/testdata/CustomerRepositoryTest/findAll-init-data")
	@ExpectedDatabase(value = "/testdata/CustomerRepositoryTest/findAll-init-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	void findPageは指定された10件を正しく取得する() {
		List<Customer> expectedList = new ArrayList<>();

		List<CustomerPhone> expectedPhones1 = new ArrayList<>();
		expectedPhones1.add(new CustomerPhone(1, 1, "07000000000", "佐藤一郎さんの電話番号１", false));
		expectedPhones1.add(new CustomerPhone(2, 1, "07000000001", "佐藤一郎さんの電話番号２", false));
		expectedPhones1.add(new CustomerPhone(3, 1, "07000000002", "佐藤一郎さんの電話番号３", false));
		List<CustomerMail> expectedMails1 = new ArrayList<>();
		expectedMails1.add(new CustomerMail(1, 1, "ichiro1@example.com", "佐藤一郎さんのメールアドレス１", false));
		expectedMails1.add(new CustomerMail(2, 1, "ichiro2@example.com", "佐藤一郎さんのメールアドレス２", false));
		expectedMails1.add(new CustomerMail(3, 1, "ichiro3@example.com", "佐藤一郎さんのメールアドレス３", false));

		Customer expectedCustomer1 = new Customer(
				1,
				"佐藤一郎",
				"サトウイチロウ",
				Date.valueOf("1993-2-20"),
				"male",
				"B",
				"埼玉県さいたま市北区〇〇町1-1-1",
				"サンプル",
				Date.valueOf("2022-3-22"),
				new Tantosha(1, "admin", new Shozoku(1, "所属1"), ROLE_ADMIN),
				expectedPhones1,
				expectedMails1);
		expectedList.add(expectedCustomer1);

		List<CustomerPhone> expectedPhones2 = new ArrayList<>();
		expectedPhones2.add(new CustomerPhone(4, 2, "08000000000", "佐藤次郎さんの電話番号１", false));
		List<CustomerMail> expectedMails2 = new ArrayList<>();
		Customer expectedCustomer2 = new Customer(
				2,
				"佐藤次郎",
				"サトウジロウ",
				Date.valueOf("1993-2-21"),
				"male",
				"A",
				"埼玉県さいたま市北区〇〇町1-1-1",
				"サンプル",
				Date.valueOf("2022-3-22"),
				new Tantosha(2, "user", new Shozoku(4, "婦人服"), ROLE_GENERAL),
				expectedPhones2,
				expectedMails2);
		expectedList.add(expectedCustomer2);

		List<CustomerPhone> expectedPhones3 = new ArrayList<>();
		expectedPhones3.add(new CustomerPhone(5, 3, "09000000000", "佐藤三郎さんの電話番号１", false));
		expectedPhones3.add(new CustomerPhone(6, 3, "09000000001", "佐藤三郎さんの電話番号２", false));
		List<CustomerMail> expectedMails3 = new ArrayList<>();
		expectedMails3.add(new CustomerMail(4, 3, "saburo1@example.com", "佐藤三郎さんのメールアドレス１", false));
		expectedMails3.add(new CustomerMail(5, 3, "saburo2@example.com", "佐藤三郎さんのメールアドレス２", false));

		Customer expectedCustomer3 = new Customer(
				3,
				"佐藤三郎",
				"サトウサブロウ",
				Date.valueOf("1993-2-22"),
				"male",
				"O",
				"埼玉県さいたま市北区〇〇町1-1-1",
				"サンプル",
				Date.valueOf("2022-3-22"),
				new Tantosha(3, "test", new Shozoku(6, "食品"), ROLE_GENERAL),
				expectedPhones3,
				expectedMails3);
		expectedList.add(expectedCustomer3);

		List<Customer> actualList = repository.findPage(PageRequest.of(0, 10));

		assertEquals(3, actualList.size());
		assertEquals(expectedList, actualList);
		assertThat(actualList.get(0)).isEqualTo(expectedCustomer1);
		assertThat(actualList.get(1)).isEqualTo(expectedCustomer2);
		assertThat(actualList.get(2)).isEqualTo(expectedCustomer3);

	}

	@Test
	@Order(9)
	@DatabaseSetup("/testdata/CustomerRepositoryTest/init-data")
	@ExpectedDatabase(value = "/testdata/CustomerRepositoryTest/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	void countはレコードの件数を正しく取得する() {
		long actual = repository.count();
		assertThat(actual).isEqualTo(20L);
	}
}
