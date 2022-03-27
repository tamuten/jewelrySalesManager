package com.jewelry.domain.repository;

import static com.jewelry.constant.Const.*;
import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
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
import com.jewelry.domain.model.Tantosha;
@SpringBootTest
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class) // DBUnitでCSVファイルを使えるよう指定。
@TestExecutionListeners({
		DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
		TransactionDbUnitTestExecutionListener.class // @DatabaseSetupや＠ExpectedDatabaseなどを使えるように指定
})
@Transactional
public class TantoshaRepositoryTest {
	@Autowired
	TantoshaRepository repository;

	@Test
	@DatabaseSetup("/testdata/TantoshaServiceTest/pageable-init-data")
	@ExpectedDatabase(value = "/testdata/TantoshaServiceTest/pageable-init-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	void findPageはpageが1でsizeが10のときテーブルのID11から20までを返す() {
		List<Tantosha> expected = new ArrayList<>();
		expected.add(new Tantosha(11, "北川景子", new Shozoku(3, "所属3"), ROLE_GENERAL));
		expected.add(new Tantosha(12, "星野源", new Shozoku(5, "家庭用品"), ROLE_GENERAL));
		expected.add(new Tantosha(13, "染谷将太", new Shozoku(1, "所属1"), ROLE_GENERAL));
		expected.add(new Tantosha(14, "加瀬亮", new Shozoku(4, "婦人服"), ROLE_GENERAL));
		expected.add(new Tantosha(15, "浜辺美波", new Shozoku(6, "食品"), ROLE_GENERAL));
		expected.add(new Tantosha(16, "中条あやみ", new Shozoku(2, "所属2"), ROLE_GENERAL));
		expected.add(new Tantosha(17, "杏", new Shozoku(3, "所属3"), ROLE_GENERAL));
		expected.add(new Tantosha(18, "東出昌大", new Shozoku(5, "家庭用品"), ROLE_GENERAL));
		expected.add(new Tantosha(19, "橋本愛", new Shozoku(1, "所属1"), ROLE_GENERAL));
		expected.add(new Tantosha(20, "のん", new Shozoku(4, "婦人服"), ROLE_GENERAL));

		Pageable pageable = PageRequest.of(1, 10);
		List<Tantosha> actual = repository.findPage(pageable);
		assertThat(actual.size()).isEqualTo(expected.size());
		assertThat(actual.get(0)).isEqualTo(expected.get(0));
		assertThat(actual.get(1)).isEqualTo(expected.get(1));
		assertThat(actual.get(2)).isEqualTo(expected.get(2));
		assertThat(actual.get(3)).isEqualTo(expected.get(3));
		assertThat(actual.get(4)).isEqualTo(expected.get(4));
		assertThat(actual.get(5)).isEqualTo(expected.get(5));
		assertThat(actual.get(6)).isEqualTo(expected.get(6));
		assertThat(actual.get(7)).isEqualTo(expected.get(7));
		assertThat(actual.get(8)).isEqualTo(expected.get(8));
		assertThat(actual.get(9)).isEqualTo(expected.get(9));
	}

	@Test
	@DatabaseSetup("/testdata/TantoshaServiceTest/init-data")
	@ExpectedDatabase(value = "/testdata/TantoshaServiceTest/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	void existsByShozokuIdは引数に指定した所属IDに該当する担当者が存在する場合Trueを返す(){
		boolean actual = repository.existsByShozokuId(1);
		assertThat(actual).isTrue();
	}

	@Test
	@DatabaseSetup("/testdata/TantoshaServiceTest/init-data")
	@ExpectedDatabase(value = "/testdata/TantoshaServiceTest/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	void existsByShozokuIdは引数に指定した所属IDに該当する担当者が存在しない場合Falseを返す(){
		boolean actual = repository.existsByShozokuId(5);
		assertThat(actual).isFalse();
	}
}
