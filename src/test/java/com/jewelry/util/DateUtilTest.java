package com.jewelry.util;

import static org.assertj.core.api.Assertions.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DateUtilTest {
	@Test
	void sqlToUtilで日付を入力し正しく変換されること() throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date expected = dateFormat.parse("2022-3-28");

		java.util.Date actual = DateUtil.sqlToUtil(Date.valueOf("2022-3-28"));
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	void sqlToUtilでnullを引数に指定した場合はnullを返すこと() {
		java.util.Date actual = DateUtil.sqlToUtil(null);
		assertThat(actual).isNull();
	}

	@Test
	void utilToSqlで日付を指定し正しく変換される() throws ParseException {
		java.sql.Date expected = Date.valueOf("2022-3-28");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date actual = DateUtil.utilToSql(dateFormat.parse("2022-3-28"));

		assertThat(actual).isEqualTo(expected);
	}

	@Test
	void utilToSqlでnullを引数に指定した場合はnullを返すこと() {
		java.sql.Date actual = DateUtil.utilToSql(null);
		assertThat(actual).isNull();
	}
}
