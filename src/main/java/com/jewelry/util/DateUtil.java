package com.jewelry.util;

import java.util.Date;

public class DateUtil {
	public static Date sqlToUtil(java.sql.Date sqlDate) {
		return new Date(sqlDate.getTime());
	}
}
