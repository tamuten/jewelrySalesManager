package com.jewelry;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

/**
 * アノテーションで指定されたテーブルの外部キーを無効にするListner
 */
public class ForeignKeyDisableTestExecutionListener extends TransactionalTestExecutionListener {

	private static final String DEFAULT_DATASOURCE_NAME = "dataSource";

	@Override
	public void beforeTestMethod(TestContext testContext) throws Exception {

		super.beforeTestMethod(testContext);

		DataSource dataSource = getDataSource(testContext);
		Connection conn = DataSourceUtils.getConnection(dataSource);
		try {
			Method method = testContext.getTestMethod();
			ForeignKey foreignKey = method.getAnnotation(ForeignKey.class);
			if (foreignKey == null) {
				return;
			}
			// 外部キーを無効にする
			//			for (String table : foreignKey.tables()) {
			//				// 使用するDBの外部キーを無効にするSQLを実行
			String sql = "SET REFERENTIAL_INTEGRITY FALSE;";
			conn.createStatement()
				.execute(sql);
			//			}
		} finally {
			DataSourceUtils.releaseConnection(conn, dataSource);
		}
	}

	private DataSource getDataSource(TestContext context) {
		DataSource dataSource;
		Map<String, DataSource> beans = context.getApplicationContext()
			.getBeansOfType(DataSource.class);
		if (beans.size() > 1) {
			dataSource = (DataSource) beans.get(DEFAULT_DATASOURCE_NAME);

			if (dataSource == null) {
				throw new NoSuchBeanDefinitionException("Unable to locate default data source.");
			}
		} else {
			dataSource = (DataSource) beans.values()
				.iterator()
				.next();
		}

		return dataSource;
	}
}
