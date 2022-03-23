package com.jewelry.domain.repository;

import static com.jewelry.constant.Const.*;
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
	//	@Autowired
	//	private JdbcTemplate jdbcTemplate;
	//
	//	@BeforeEach
	//	void setup() {
	//		jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");
	//	}
	//
	//	@AfterEach
	//	void after() {
	//		jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
	//	}

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
		正常に登録(CHARS_50, CHARS_100, "2000-1-1", CHARS_10, CHARS_5, CHARS_100, CHARS_200, 2, "2000-1-1");
		正常に登録(CHARS_EMPTY, CHARS_EMPTY, CHARS_EMPTY, CHARS_EMPTY, CHARS_EMPTY, CHARS_EMPTY, CHARS_EMPTY, 2, "2000-1-1");
		正常に登録("山本美月", null, null, null, null, null, null, 3, "2000-1-1");

		// TODO: 異常系
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
		expectedPhones1.add(new CustomerPhone(1, 1, "07000000000", "佐藤一郎さんの電話番号１"));
		expectedPhones1.add(new CustomerPhone(2, 1, "07000000001", "佐藤一郎さんの電話番号２"));
		expectedPhones1.add(new CustomerPhone(3, 1, "07000000002", "佐藤一郎さんの電話番号３"));
		List<CustomerMail> expectedMails1 = new ArrayList<>();
		expectedMails1.add(new CustomerMail(1, 1, "ichiro1@example.com", "佐藤一郎さんのメールアドレス１"));
		expectedMails1.add(new CustomerMail(2, 1, "ichiro2@example.com", "佐藤一郎さんのメールアドレス２"));
		expectedMails1.add(new CustomerMail(3, 1, "ichiro3@example.com", "佐藤一郎さんのメールアドレス３"));
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
		expectedPhones2.add(new CustomerPhone(4, 2, "08000000000", "佐藤次郎さんの電話番号１"));
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
		expectedPhones3.add(new CustomerPhone(5, 3, "09000000000", "佐藤三郎さんの電話番号１"));
		expectedPhones3.add(new CustomerPhone(6, 3, "09000000001", "佐藤三郎さんの電話番号２"));
		List<CustomerMail> expectedMails3 = new ArrayList<>();
		expectedMails3.add(new CustomerMail(4, 3, "saburo1@example.com", "佐藤三郎さんのメールアドレス１"));
		expectedMails3.add(new CustomerMail(5, 3, "saburo2@example.com", "佐藤三郎さんのメールアドレス２"));
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
		expectedPhones.add(new CustomerPhone(1, 1, "07000000000", "メモ"));
		expectedPhones.add(new CustomerPhone(2, 1, "07000000001", "メモ"));
		expectedPhones.add(new CustomerPhone(3, 1, "07000000002", "メモ"));
		List<CustomerMail> expectedMails = new ArrayList<>();
		expectedMails.add(new CustomerMail(1, 1, "sample1@example.com", "メモ"));
		expectedMails.add(new CustomerMail(2, 1, "sample2@example.com", "メモ"));
		expectedMails.add(new CustomerMail(3, 1, "sample3@example.com", "メモ"));
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

	void 更新のテスト() {

	}

	void 削除のテスト() {

	}

}
