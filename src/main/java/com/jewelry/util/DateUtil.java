package com.jewelry.util;

public class DateUtil {
	/**
	 * java.sql.Date型をjava.util.Date型に変換します。<br>
	 * 引数にnullが指定された場合はnullを返却します。
	 *
	 * @param sqlDate
	 * @return
	 */
	public static java.util.Date sqlToUtil(java.sql.Date sqlDate) {
		if (sqlDate == null) {
			return null;
		}
		return new java.util.Date(sqlDate.getTime());
	}

	/**
	 * java.util.Date型をjava.sql.Date型に変換します<br>
	 * 引数にnullが指定された場合はnullを返却します。
	 *
	 * @param utilDate
	 * @return
	 */
	public static java.sql.Date utilToSql(java.util.Date utilDate) {
		if (utilDate == null) {
			return null;
		}
		return new java.sql.Date(utilDate.getTime());
	}
}
