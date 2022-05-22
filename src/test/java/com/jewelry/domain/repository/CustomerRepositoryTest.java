package com.jewelry.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.jewelry.dataset.CsvDataSetLoader;
import com.jewelry.domain.model.Customer;
import com.jewelry.domain.model.CustomerSearch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class) // DBUnitでCSVファイルを使えるよう指定。
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
    TransactionDbUnitTestExecutionListener.class // @DatabaseSetupや＠ExpectedDatabaseなどを使えるように指定
})
@Transactional
public class CustomerRepositoryTest {
  @Autowired
  private CustomerRepository repository;

  // @Test
  // @DatabaseSetup("/testdata/CustomerRepositoryTest/init-data")
  // @ExpectedDatabase(value =
  // "/testdata/CustomerRepositoryTest/after-create-data", assertionMode =
  // DatabaseAssertionMode.NON_STRICT_UNORDERED)
  // void 顧客作成のテスト_DBチェック() {
  // // Setup
  // Tantosha newTantosha = Tantosha.builder()
  // .id(1)
  // .build();
  // Customer newCustomer = Customer.builder()
  // .name("山本香織")
  // .nameKana("ヤマモトカオリ")
  // .birthday(Date.valueOf("2000-3-21"))
  // .gender("female")
  // .bloodType("A")
  // .phoneNo1("00000000000")
  // .phoneNo2("00000000001")
  // .mailAddress("mail@example.com")
  // .address("埼玉県上尾市瓦葺1111-1-101")
  // .memo("新規追加")
  // .tantosha(newTantosha)
  // .signupDate(Date.valueOf("2022-3-21"))
  // .build();
  //
  // // Execute
  // int createdCount = repository.create(newCustomer);
  //
  // // Verify
  // assertThat(createdCount).isEqualTo(1);
  // }
  //
  // @Test
  // void 顧客作成のテスト() {
  // // 正常系
  // 正常に登録(CHARS_50, CHARS_100, "2000-1-1", CHARS_10, CHARS_5, CHARS_20, CHARS_20,
  // CHARS_20, CHARS_100, CHARS_100,
  // CHARS_200, 2, "2000-1-1");
  // 正常に登録(CHARS_EMPTY, CHARS_EMPTY, CHARS_EMPTY, CHARS_EMPTY, CHARS_EMPTY,
  // CHARS_EMPTY, CHARS_EMPTY, CHARS_EMPTY,
  // CHARS_EMPTY, CHARS_EMPTY, CHARS_EMPTY, 2, "2000-1-1");
  // 正常に登録("山本美月", null, null, null, null, null, null, null, null, null, null, 3,
  // "2000-1-1");
  // 正常に登録("山本美月", null, null, null, null, null, null, null, null, null, null,
  // null, "2000-1-1");
  //
  // // 異常系
  // 登録に失敗(CHARS_51, CHARS_101, "2020-1-1", CHARS_11, CHARS_6, CHARS_21, CHARS_21,
  // CHARS_21, CHARS_101, CHARS_101,
  // CHARS_201, null, "2020-1-31");
  // 登録に失敗(null, CHARS_100, "2020-1-1", CHARS_10, CHARS_5, CHARS_20, CHARS_20,
  // CHARS_20, CHARS_100, CHARS_100,
  // CHARS_200, null, "2020-1-31");
  // 登録に失敗(CHARS_51, CHARS_100, "2020-1-1", CHARS_10, CHARS_5, CHARS_20, CHARS_20,
  // CHARS_20, CHARS_100, CHARS_100,
  // CHARS_200, null, "2020-1-31");
  // 登録に失敗(CHARS_50, CHARS_101, "2020-1-1", CHARS_10, CHARS_5, CHARS_20, CHARS_20,
  // CHARS_20, CHARS_100, CHARS_100,
  // CHARS_200, null, "2020-1-31");
  // 登録に失敗(CHARS_50, CHARS_100, "2020-1-1", CHARS_11, CHARS_5, CHARS_20, CHARS_20,
  // CHARS_20, CHARS_100, CHARS_100,
  // CHARS_200, null, "2020-1-31");
  // 登録に失敗(CHARS_50, CHARS_100, "2020-1-1", CHARS_10, CHARS_6, CHARS_20, CHARS_20,
  // CHARS_20, CHARS_100, CHARS_100,
  // CHARS_200, null, "2020-1-31");
  // 登録に失敗(CHARS_50, CHARS_100, "2020-1-1", CHARS_10, CHARS_5, CHARS_21, CHARS_20,
  // CHARS_20, CHARS_100, CHARS_100,
  // CHARS_200, null, "2020-1-31");
  // 登録に失敗(CHARS_50, CHARS_100, "2020-1-1", CHARS_10, CHARS_5, CHARS_20, CHARS_21,
  // CHARS_20, CHARS_100, CHARS_100,
  // CHARS_200, null, "2020-1-31");
  // 登録に失敗(CHARS_50, CHARS_100, "2020-1-1", CHARS_10, CHARS_5, CHARS_20, CHARS_20,
  // CHARS_21, CHARS_100, CHARS_100,
  // CHARS_200, null, "2020-1-31");
  // 登録に失敗(CHARS_50, CHARS_100, "2020-1-1", CHARS_10, CHARS_5, CHARS_20, CHARS_20,
  // CHARS_20, CHARS_101, CHARS_100,
  // CHARS_200, null, "2020-1-31");
  // 登録に失敗(CHARS_50, CHARS_100, "2020-1-1", CHARS_10, CHARS_5, CHARS_20, CHARS_20,
  // CHARS_20, CHARS_100, CHARS_101,
  // CHARS_200, null, "2020-1-31");
  // 登録に失敗(CHARS_50, CHARS_100, "2020-1-1", CHARS_10, CHARS_5, CHARS_20, CHARS_20,
  // CHARS_20, CHARS_100, CHARS_100,
  // CHARS_201, null, "2020-1-31");
  // 登録に失敗(CHARS_50, CHARS_100, "2020-1-1", CHARS_10, CHARS_5, CHARS_20, CHARS_20,
  // CHARS_20, CHARS_100, CHARS_100,
  // CHARS_200, null, null);
  // }
  //
  // /**
  // * createメソッドが正常に実行されるか検証します
  // *
  // * @param name 顧客名
  // * @param kana 顧客名カナ
  // * @param birthdayStr 生年月日（文字列）
  // * @param gender 性別
  // * @param bloodType 血液型
  // * @param address 住所
  // * @param memo メモ
  // * @param tantoshaId 担当者ID
  // * @param signupDateStr 登録日（文字列）
  // */
  // void 正常に登録(String name, String kana, String birthdayStr, String gender,
  // String bloodType, String phoneNo1,
  // String phoneNo2, String phoneNo3, String mailAddress, String address,
  // String memo, Integer tantoshaId, String signupDateStr) {
  // Date birthday = null;
  // if (!(birthdayStr == null || birthdayStr.equals(""))) {
  // birthday = Date.valueOf(birthdayStr);
  // }
  //
  // Date signupDate = null;
  // if (!(signupDateStr == null || signupDateStr.equals(""))) {
  // signupDate = Date.valueOf(signupDateStr);
  // }
  //
  // Tantosha newTantosha = Tantosha.builder()
  // .id(tantoshaId)
  // .build();
  // Customer newCustomer = Customer.builder()
  // .name(name)
  // .nameKana(kana)
  // .birthday(birthday)
  // .gender(gender)
  // .bloodType(bloodType)
  // .phoneNo1(phoneNo1)
  // .phoneNo2(phoneNo2)
  // .phoneNo3(phoneNo3)
  // .mailAddress(mailAddress)
  // .address(address)
  // .memo(memo)
  // .tantosha(newTantosha)
  // .signupDate(signupDate)
  // .build();
  // int createdCount = repository.create(newCustomer);
  // assertEquals(createdCount, 1);
  // }
  //
  // /**
  // * createメソッドが異常終了するか検証します
  // *
  // * @param name 顧客名
  // * @param kana 顧客名カナ
  // * @param birthdayStr 生年月日（文字列）
  // * @param gender 性別
  // * @param bloodType 血液型
  // * @param address 住所
  // * @param memo メモ
  // * @param tantoshaId 担当者ID
  // * @param signupDateStr 登録日（文字列）
  // */
  // void 登録に失敗(String name, String kana, String birthdayStr, String gender,
  // String bloodType, String phoneNo1,
  // String phoneNo2, String phoneNo3, String mailAddress, String address,
  // String memo, Integer tantoshaId, String signupDateStr) {
  // Date birthday = null;
  // if (!(birthdayStr == null || birthdayStr.equals(""))) {
  // birthday = Date.valueOf(birthdayStr);
  // }
  //
  // Date signupDate = null;
  // if (!(signupDateStr == null || signupDateStr.equals(""))) {
  // signupDate = Date.valueOf(signupDateStr);
  // }
  //
  // Tantosha newTantosha = Tantosha.builder()
  // .id(tantoshaId)
  // .build();
  // Customer newCustomer = Customer.builder()
  // .name(name)
  // .nameKana(kana)
  // .birthday(birthday)
  // .gender(gender)
  // .bloodType(bloodType)
  // .phoneNo1(phoneNo1)
  // .phoneNo2(phoneNo2)
  // .phoneNo3(phoneNo3)
  // .mailAddress(mailAddress)
  // .address(address)
  // .memo(memo)
  // .tantosha(newTantosha)
  // .signupDate(signupDate)
  // .build();
  // assertThrows(DataIntegrityViolationException.class, () ->
  // repository.create(newCustomer));
  // }
  //
  // private static final List<Customer> FOR_FINDALL = new ArrayList<Customer>() {
  // {
  // add(Customer.builder()
  // .id(1)
  // .name("佐藤一郎")
  // .nameKana("サトウイチロウ")
  // .birthday(Date.valueOf("1993-2-20"))
  // .gender("male")
  // .bloodType("B")
  // .phoneNo1("00000000000")
  // .phoneNo2("00000000001")
  // .phoneNo3("00000000002")
  // .mailAddress("mail@example.com")
  // .address("埼玉県さいたま市北区〇〇町1-1-1")
  // .memo("サンプル")
  // .signupDate(Date.valueOf("2022-3-22"))
  // .tantosha(new Tantosha(1, "admin", new Shozoku(1, "所属1"), ROLE_ADMIN))
  // .build());
  // add(Customer.builder()
  // .id(2)
  // .name("佐藤次郎")
  // .nameKana("サトウジロウ")
  // .birthday(Date.valueOf("1993-2-21"))
  // .gender("male")
  // .bloodType("A")
  // .phoneNo1("00000000000")
  // .phoneNo2("00000000001")
  // .phoneNo3("00000000002")
  // .mailAddress("mail@example.com")
  // .address("埼玉県さいたま市北区〇〇町1-1-1")
  // .memo("サンプル")
  // .signupDate(Date.valueOf("2022-3-22"))
  // .tantosha(new Tantosha(2, "user", new Shozoku(4, "婦人服"), ROLE_GENERAL))
  // .build());
  // add(Customer.builder()
  // .id(3)
  // .name("佐藤三郎")
  // .nameKana("サトウサブロウ")
  // .birthday(Date.valueOf("1993-2-22"))
  // .gender("male")
  // .bloodType("O")
  // .phoneNo1("00000000000")
  // .phoneNo2("00000000001")
  // .phoneNo3("00000000002")
  // .mailAddress("mail@example.com")
  // .address("埼玉県さいたま市北区〇〇町1-1-1")
  // .memo("サンプル")
  // .signupDate(Date.valueOf("2022-3-22"))
  // .tantosha(new Tantosha(3, "test", new Shozoku(6, "食品"), ROLE_GENERAL))
  // .build());
  // }
  // };
  //
  // private static final Customer FOR_FINDBYPK = Customer.builder()
  // .id(1)
  // .name("佐藤一郎")
  // .nameKana("サトウイチロウ")
  // .birthday(Date.valueOf("1993-2-20"))
  // .gender("male")
  // .bloodType("B")
  // .phoneNo1("00000000000")
  // .phoneNo2("00000000001")
  // .phoneNo3("00000000002")
  // .mailAddress("mail@example.com")
  // .address("埼玉県さいたま市北区〇〇町1-1-1")
  // .memo("サンプル")
  // .signupDate(Date.valueOf("2022-3-22"))
  // .tantosha(new Tantosha(1, "admin", new Shozoku(1, "所属1"), ROLE_ADMIN))
  // .build();
  //
  // @Test
  // @DatabaseSetup("/testdata/CustomerRepositoryTest/findAll-init-data")
  // @ExpectedDatabase(value =
  // "/testdata/CustomerRepositoryTest/findAll-init-data", assertionMode =
  // DatabaseAssertionMode.NON_STRICT_UNORDERED)
  // void 全件選択のテスト() {
  // // Execute
  // List<Customer> actualList = repository.findAll();
  //
  // // Verify
  // assertEquals(3, actualList.size());
  // assertEquals(FOR_FINDALL, actualList);
  // }
  //
  // @Test
  // @DatabaseSetup("/testdata/CustomerRepositoryTest/init-data")
  // @ExpectedDatabase(value = "/testdata/CustomerRepositoryTest/init-data",
  // assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
  // void 一件選択のテスト() {
  // // Execute
  // Customer actual = repository.findByPk(1);
  //
  // // Verify
  // assertThat(actual.getId()).isEqualTo(FOR_FINDBYPK.getId());
  // assertThat(actual.getName()).isEqualTo(FOR_FINDBYPK.getName());
  // assertThat(actual.getNameKana()).isEqualTo(FOR_FINDBYPK.getNameKana());
  // assertThat(actual.getBirthday()).isEqualTo(FOR_FINDBYPK.getBirthday());
  // assertThat(actual.getGender()).isEqualTo(FOR_FINDBYPK.getGender());
  // assertThat(actual.getBloodType()).isEqualTo(FOR_FINDBYPK.getBloodType());
  // assertThat(actual.getPhoneNo1()).isEqualTo(FOR_FINDBYPK.getPhoneNo1());
  // assertThat(actual.getPhoneNo2()).isEqualTo(FOR_FINDBYPK.getPhoneNo2());
  // assertThat(actual.getPhoneNo3()).isEqualTo(FOR_FINDBYPK.getPhoneNo3());
  // assertThat(actual.getMailAddress()).isEqualTo(FOR_FINDBYPK.getMailAddress());
  // assertThat(actual.getAddress()).isEqualTo(FOR_FINDBYPK.getAddress());
  // assertThat(actual.getMemo()).isEqualTo(FOR_FINDBYPK.getMemo());
  // assertThat(actual.getSignupDate()).isEqualTo(FOR_FINDBYPK.getSignupDate());
  // assertThat(actual.getTantosha()).isEqualTo(FOR_FINDBYPK.getTantosha());
  // }
  //
  // @Test
  // @DatabaseSetup("/testdata/CustomerRepositoryTest/init-data")
  // @ExpectedDatabase(value =
  // "/testdata/CustomerRepositoryTest/after-update-data", assertionMode =
  // DatabaseAssertionMode.NON_STRICT_UNORDERED)
  // void updateで全てのパラメータを更新しDBを検証() {
  // Customer update = Customer.builder()
  // .id(16)
  // .name("田中葵")
  // .nameKana("タナカアオイ")
  // .birthday(Date.valueOf("1993-3-7"))
  // .gender("female")
  // .bloodType("B")
  // .phoneNo1("11111111111")
  // .phoneNo2("11111111112")
  // .phoneNo3("11111111113")
  // .mailAddress("update@example.com")
  // .address("埼玉県さいたま市北区〇〇町1-2-3")
  // .memo("サンプル変更")
  // .tantosha(Tantosha.builder()
  // .id(2)
  // .build())
  // .build();
  //
  // int updateCnt = repository.update(update);
  // assertThat(updateCnt).isEqualTo(1);
  // }
  //
  // @Test
  // @DatabaseSetup("/testdata/CustomerRepositoryTest/init-data")
  // @ExpectedDatabase(value =
  // "/testdata/CustomerRepositoryTest/after-part-update-data", assertionMode =
  // DatabaseAssertionMode.NON_STRICT_UNORDERED)
  // void updateで住所を変更しDBを検証() {
  // Customer update = Customer.builder()
  // .id(16)
  // .name("田中一郎")
  // .nameKana("タナカイチロウ")
  // .birthday(Date.valueOf("1993-2-7"))
  // .gender("male")
  // .bloodType("AB")
  // .phoneNo1("00000000000")
  // .phoneNo2("00000000001")
  // .phoneNo3("00000000002")
  // .mailAddress("mail@example.com")
  // .address("埼玉県さいたま市北区〇〇町1-2-4")
  // .memo("サンプル")
  // .tantosha(Tantosha.builder()
  // .id(1)
  // .build())
  // .build();
  //
  // int updateCnt = repository.update(update);
  // assertThat(updateCnt).isEqualTo(1);
  // }

  @Test
  @DatabaseSetup("/testdata/CustomerRepositoryTest/init-data")
  @ExpectedDatabase(value = "/testdata/CustomerRepositoryTest/after-delete-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
  void deleteで一件正しく削除されたか検証() {
    int deleteCnt = repository.delete(20);
    assertThat(deleteCnt).isEqualTo(1);
  }

  // @Test
  // @DatabaseSetup("/testdata/CustomerRepositoryTest/findAll-init-data")
  // @ExpectedDatabase(value =
  // "/testdata/CustomerRepositoryTest/findAll-init-data", assertionMode =
  // DatabaseAssertionMode.NON_STRICT_UNORDERED)
  // void findPageは指定された10件を正しく取得する() {
  // // Execute
  // List<Customer> actualList = repository.findPage(PageRequest.of(0, 10));
  //
  // // Verify
  // assertEquals(3, actualList.size());
  // assertThat(actualList.get(0)).isEqualTo(FOR_FINDALL.get(0));
  // assertThat(actualList.get(1)).isEqualTo(FOR_FINDALL.get(1));
  // assertThat(actualList.get(2)).isEqualTo(FOR_FINDALL.get(2));
  // }

  @Test
  @DatabaseSetup("/testdata/CustomerRepositoryTest/init-data")
  @ExpectedDatabase(value = "/testdata/CustomerRepositoryTest/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
  void countはレコードの件数を正しく取得する() {
    long actual = repository.count();
    assertThat(actual).isEqualTo(20L);
  }

  @Test
  @DatabaseSetup("/testdata/CustomerRepositoryTest/init-data")
  @ExpectedDatabase(value = "/testdata/CustomerRepositoryTest/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
  void searchCountは田中で5を返す() {
    CustomerSearch customerSearch = new CustomerSearch();
    customerSearch.setSearchName("田中");
    long actual = repository.searchCount(customerSearch);

    assertThat(actual).isEqualTo(5L);
  }

  @Test
  @DatabaseSetup("/testdata/CustomerRepositoryTest/init-data")
  @ExpectedDatabase(value = "/testdata/CustomerRepositoryTest/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
  void searchCountはnullで20を返す() {
    CustomerSearch customerSearch = new CustomerSearch();
    customerSearch.setSearchName(null);
    long actual = repository.searchCount(customerSearch);

    assertThat(actual).isEqualTo(20L);
  }

  @Test
  @DatabaseSetup("/testdata/CustomerRepositoryTest/init-data")
  @ExpectedDatabase(value = "/testdata/CustomerRepositoryTest/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
  void searchは田中で5件返す() {
    CustomerSearch customerSearch = new CustomerSearch();
    customerSearch.setSearchName("田中");

    List<Customer> actualList = repository.search(customerSearch, PageRequest.of(0, 10));
    assertThat(actualList.size()).isEqualTo(5);
  }

  @Test
  @DatabaseSetup("/testdata/CustomerRepositoryTest/init-data")
  @ExpectedDatabase(value = "/testdata/CustomerRepositoryTest/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
  void searchはnullで10件返す() {
    CustomerSearch customerSearch = new CustomerSearch();
    customerSearch.setSearchName(null);

    List<Customer> actualList = repository.search(customerSearch, PageRequest.of(0, 10));
    assertThat(actualList.size()).isEqualTo(10);
  }

}
