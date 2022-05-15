package com.jewelry.domain.repository;

import static org.assertj.core.api.Assertions.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
import com.jewelry.domain.model.Sales;

@SpringBootTest
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class) // DBUnitでCSVファイルを使えるよう指定。
@TestExecutionListeners({
		DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
		TransactionDbUnitTestExecutionListener.class // @DatabaseSetupや＠ExpectedDatabaseなどを使えるように指定
})
@Transactional
public class SalesRepositoryTest {
	@Autowired
	private SalesRepository repository;

	private static final List<Sales> FOR_FINDPAGE = new ArrayList<Sales>() {
		{
			add(new Sales(11, Date.valueOf("2022-1-11"), Date.valueOf("2022-1-12"), "紳士服", "鈴木", "pt", "ag", "au",
					"SRW61", "119861", 500100, null, Date.valueOf("2022-1-11"), "541379", new Customer(11, "伊藤一郎")));
			add(new Sales(12, Date.valueOf("2022-1-12"), Date.valueOf("2022-1-13"), "屋上", "木村", "ag", "au", "pt",
					"SRW62", "119862", 500110, 400110, null, "541380", new Customer(12, "伊藤次郎")));
			add(new Sales(13, Date.valueOf("2022-1-13"), Date.valueOf("2022-1-14"), "スポーツ用品", "中村", "au", "pt", "ag",
					"SRW63", "119863", 500120, 400120, Date.valueOf("2022-1-13"), null, new Customer(13, "伊藤三郎")));
			add(new Sales(14, Date.valueOf("2022-1-14"), Date.valueOf("2022-1-15"), "化粧品", "山本", "pt", "ag", "au",
					"SRW64", "119864", 500130, 400130, Date.valueOf("2022-1-14"), "541382", new Customer(14, "伊藤四郎")));
			add(new Sales(15, Date.valueOf("2022-1-15"), Date.valueOf("2022-1-16"), "食料品", "田中", "ag", "au", "pt",
					"SRW65", "119865", 500140, 400140, Date.valueOf("2022-1-15"), "541383", new Customer(15, "伊藤沙織")));
			add(new Sales(16, Date.valueOf("2022-1-16"), Date.valueOf("2022-1-17"), "催事", "髙橋", "au", "pt", "ag",
					"SRW66", "119866", 500150, 400150, Date.valueOf("2022-1-16"), "541384", new Customer(16, "田中一郎")));
			add(new Sales(17, Date.valueOf("2022-1-17"), Date.valueOf("2022-1-18"), "婦人服", "佐藤", "pt", "ag", "au",
					"SRW67", "119867", 500160, 400160, Date.valueOf("2022-1-17"), "541385", new Customer(17, "田中次郎")));
			add(new Sales(18, Date.valueOf("2022-1-18"), Date.valueOf("2022-1-19"), "紳士服", "鈴木", "ag", "au", "pt",
					"SRW68", "119868", 500170, 400170, Date.valueOf("2022-1-18"), "541386", new Customer(18, "田中三郎")));
			add(new Sales(19, Date.valueOf("2022-1-19"), Date.valueOf("2022-1-20"), "屋上", "木村", "au", "pt", "ag",
					"SRW69", "119869", 500180, 400180, Date.valueOf("2022-1-19"), "541387", new Customer(19, "田中四郎")));
			add(new Sales(20, Date.valueOf("2022-1-20"), Date.valueOf("2022-1-21"), "スポーツ用品", "中村", "pt", "ag", "au",
					"SRW70", "119870", 500190, 400190, Date.valueOf("2022-1-20"), "541388", new Customer(20, "田中美咲")));
		}
	};

	private static final List<Sales> FOR_FIND_BY_CUSTOMER = new ArrayList<Sales>() {
		{
			add(new Sales(4, Date.valueOf("2022-1-4"), Date.valueOf("2022-1-5"), "紳士服", null, "au", "pt", "ag",
					"SRW54", "119854", 500030, 400030, Date.valueOf("2022-1-4"), "541372", new Customer(4, "佐藤四郎")));
			add(new Sales(25, Date.valueOf("2022-1-25"), Date.valueOf("2022-1-26"), "紳士服", "鈴木", "au", "pt", "ag",
					"SRW75", "119875", 500240, 400240, Date.valueOf("2022-1-25"), "541393", new Customer(4, "佐藤四郎")));
			add(new Sales(46, Date.valueOf("2022-2-15"), Date.valueOf("2022-2-16"), "紳士服", "鈴木", "au", "pt", "ag",
					"SRW96", "119896", 500450, 400450, Date.valueOf("2022-2-15"), "541414", new Customer(4, "佐藤四郎")));
			add(new Sales(67, Date.valueOf("2022-3-8"), Date.valueOf("2022-3-9"), "紳士服", "鈴木", "au", "pt", "ag",
					"SRW117", "119917", 500660, 400660, Date.valueOf("2022-3-8"), "541435", new Customer(4, "佐藤四郎")));
			add(new Sales(88, Date.valueOf("2022-3-29"), Date.valueOf("2022-3-30"), "紳士服", "鈴木", "au", "pt", "ag",
					"SRW138", "119938", 500870, 400870, Date.valueOf("2022-3-29"), "541456", new Customer(4, "佐藤四郎")));
			add(new Sales(109, Date.valueOf("2022-4-19"), Date.valueOf("2022-4-20"), "紳士服", "鈴木", "au", "pt", "ag",
					"SRW159", "119959", 501080, 401080, Date.valueOf("2022-4-19"), "541477", new Customer(4, "佐藤四郎")));
			add(new Sales(130, Date.valueOf("2022-5-10"), Date.valueOf("2022-5-11"), "紳士服", "鈴木", "au", "pt", "ag",
					"SRW180", "119980", 501290, 401290, Date.valueOf("2022-5-10"), "541498", new Customer(4, "佐藤四郎")));
		}
	};

	private static final List<Sales> FOR_CREATE_ALL = new ArrayList<Sales>() {
		{
			add(new Sales(null, Date.valueOf("2022-5-30"), Date.valueOf("2022-5-31"), "紳士服", null, "au", "pt", "ag",
					"SRW48", "119854", 500030, 400030, Date.valueOf("2022-1-4"), "541372", new Customer(4, "佐藤四郎")));
			add(new Sales(null, Date.valueOf("2022-5-31"), Date.valueOf("2022-6-1"), "紳士服", "鈴木", "au", "pt", "ag",
					"SRW48", "119875", 500240, 400240, Date.valueOf("2022-1-25"), "541393", new Customer(4, "佐藤四郎")));
			add(new Sales(null, Date.valueOf("2022-6-1"), Date.valueOf("2022-6-2"), "紳士服", "鈴木", "au", "pt", "ag",
					"SRW48", "119896", 500450, 400450, Date.valueOf("2022-2-15"), "541414", new Customer(4, "佐藤四郎")));
		}
	};

	@Test
	@DatabaseSetup("/testdata/SalesRepositoryTest/init-data")
	@ExpectedDatabase(value = "/testdata/SalesRepositoryTest/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	void countはテーブルの件数を正しく取得する() {
		// Execute
		long actual = repository.count();
		// Verify
		assertThat(actual).isEqualTo(149L);
	}

	@Test
	@DatabaseSetup("/testdata/SalesRepositoryTest/init-data")
	@ExpectedDatabase(value = "/testdata/SalesRepositoryTest/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	void countByCustomerはcustomerIdに紐づく件数を正しく取得する() {
		// Execute
		long actual = repository.countByCustomer(4);
		// Verify
		assertThat(actual).isEqualTo(7L);
	}

	@Test
	@DatabaseSetup("/testdata/SalesRepositoryTest/init-data")
	@ExpectedDatabase(value = "/testdata/SalesRepositoryTest/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	void findPageは指定された範囲を正しく取得する() {
		// Execute
		List<Sales> actual = repository.findPage(PageRequest.of(1, 10));

		// Verify
		assertThat(actual.size()).isEqualTo(10);

		assertThat(actual.get(0)
			.getId()).isEqualTo(FOR_FINDPAGE.get(0)
				.getId());
		assertThat(actual.get(0)
			.getJutyuDate()).isEqualTo(FOR_FINDPAGE.get(0)
				.getJutyuDate());
		assertThat(actual.get(0)
			.getKeijoDate()).isEqualTo(FOR_FINDPAGE.get(0)
				.getKeijoDate());
		assertThat(actual.get(0)
			.getUriba()).isEqualTo(FOR_FINDPAGE.get(0)
				.getUriba());
		assertThat(actual.get(0)
			.getKsTanto()).isEqualTo(FOR_FINDPAGE.get(0)
				.getKsTanto());
		assertThat(actual.get(0)
			.getHinmei1()).isEqualTo(FOR_FINDPAGE.get(0)
				.getHinmei1());
		assertThat(actual.get(0)
			.getHinmei2()).isEqualTo(FOR_FINDPAGE.get(0)
				.getHinmei2());
		assertThat(actual.get(0)
			.getHinmei3()).isEqualTo(FOR_FINDPAGE.get(0)
				.getHinmei3());
		assertThat(actual.get(0)
			.getNumber1()).isEqualTo(FOR_FINDPAGE.get(0)
				.getNumber1());
		assertThat(actual.get(0)
			.getNumber2()).isEqualTo(FOR_FINDPAGE.get(0)
				.getNumber2());
		assertThat(actual.get(0)
			.getJodai()).isEqualTo(FOR_FINDPAGE.get(0)
				.getJodai());
		assertThat(actual.get(0)
			.getGedai()).isEqualTo(FOR_FINDPAGE.get(0)
				.getGedai());
		assertThat(actual.get(0)
			.getShikiriDate()).isEqualTo(FOR_FINDPAGE.get(0)
				.getShikiriDate());
		assertThat(actual.get(0)
			.getShikiriNo()).isEqualTo(FOR_FINDPAGE.get(0)
				.getShikiriNo());
		assertThat(actual.get(0)
			.getCustomer()).isEqualTo(FOR_FINDPAGE.get(0)
				.getCustomer());

		assertThat(actual.get(1)).isEqualTo(FOR_FINDPAGE.get(1));
		assertThat(actual.get(2)).isEqualTo(FOR_FINDPAGE.get(2));
		assertThat(actual.get(3)).isEqualTo(FOR_FINDPAGE.get(3));
		assertThat(actual.get(4)).isEqualTo(FOR_FINDPAGE.get(4));
		assertThat(actual.get(5)).isEqualTo(FOR_FINDPAGE.get(5));
		assertThat(actual.get(6)).isEqualTo(FOR_FINDPAGE.get(6));
		assertThat(actual.get(7)).isEqualTo(FOR_FINDPAGE.get(7));
		assertThat(actual.get(8)).isEqualTo(FOR_FINDPAGE.get(8));
		assertThat(actual.get(9)).isEqualTo(FOR_FINDPAGE.get(9));

	}

	@Test
	@DatabaseSetup("/testdata/SalesRepositoryTest/init-data")
	@ExpectedDatabase(value = "/testdata/SalesRepositoryTest/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	void findByCustomerは指定した顧客に紐づくレコードを指定された範囲で正しく取得する() {
		// Execute
		List<Sales> actual = repository.findByCustomer(PageRequest.of(0, 10), 4);

		// Verify
		assertThat(actual.size()).isEqualTo(7);

		assertThat(actual.get(0)
			.getId()).isEqualTo(FOR_FIND_BY_CUSTOMER.get(0)
				.getId());
		assertThat(actual.get(0)
			.getJutyuDate()).isEqualTo(FOR_FIND_BY_CUSTOMER.get(0)
				.getJutyuDate());
		assertThat(actual.get(0)
			.getKeijoDate()).isEqualTo(FOR_FIND_BY_CUSTOMER.get(0)
				.getKeijoDate());
		assertThat(actual.get(0)
			.getUriba()).isEqualTo(FOR_FIND_BY_CUSTOMER.get(0)
				.getUriba());
		assertThat(actual.get(0)
			.getKsTanto()).isEqualTo(FOR_FIND_BY_CUSTOMER.get(0)
				.getKsTanto());
		assertThat(actual.get(0)
			.getHinmei1()).isEqualTo(FOR_FIND_BY_CUSTOMER.get(0)
				.getHinmei1());
		assertThat(actual.get(0)
			.getHinmei2()).isEqualTo(FOR_FIND_BY_CUSTOMER.get(0)
				.getHinmei2());
		assertThat(actual.get(0)
			.getHinmei3()).isEqualTo(FOR_FIND_BY_CUSTOMER.get(0)
				.getHinmei3());
		assertThat(actual.get(0)
			.getNumber1()).isEqualTo(FOR_FIND_BY_CUSTOMER.get(0)
				.getNumber1());
		assertThat(actual.get(0)
			.getNumber2()).isEqualTo(FOR_FIND_BY_CUSTOMER.get(0)
				.getNumber2());
		assertThat(actual.get(0)
			.getJodai()).isEqualTo(FOR_FIND_BY_CUSTOMER.get(0)
				.getJodai());
		assertThat(actual.get(0)
			.getGedai()).isEqualTo(FOR_FIND_BY_CUSTOMER.get(0)
				.getGedai());
		assertThat(actual.get(0)
			.getShikiriDate()).isEqualTo(FOR_FIND_BY_CUSTOMER.get(0)
				.getShikiriDate());
		assertThat(actual.get(0)
			.getShikiriNo()).isEqualTo(FOR_FIND_BY_CUSTOMER.get(0)
				.getShikiriNo());
		assertThat(actual.get(0)
			.getCustomer()).isEqualTo(FOR_FIND_BY_CUSTOMER.get(0)
				.getCustomer());

		assertThat(actual.get(1)).isEqualTo(FOR_FIND_BY_CUSTOMER.get(1));
		assertThat(actual.get(2)).isEqualTo(FOR_FIND_BY_CUSTOMER.get(2));
		assertThat(actual.get(3)).isEqualTo(FOR_FIND_BY_CUSTOMER.get(3));
		assertThat(actual.get(4)).isEqualTo(FOR_FIND_BY_CUSTOMER.get(4));
		assertThat(actual.get(5)).isEqualTo(FOR_FIND_BY_CUSTOMER.get(5));
		assertThat(actual.get(6)).isEqualTo(FOR_FIND_BY_CUSTOMER.get(6));
	}

	@Test
	@DatabaseSetup("/testdata/SalesRepositoryTest/init-data")
	@ExpectedDatabase(value = "/testdata/SalesRepositoryTest/after-createAll-data", assertionMode = DatabaseAssertionMode.NON_STRICT)
	void createAllは過不足なくDBに登録される() {
		// Exucute
		int createdCnt = repository.createAll(FOR_CREATE_ALL);

		// Verify
		assertThat(createdCnt).isEqualTo(3);
	}

}
