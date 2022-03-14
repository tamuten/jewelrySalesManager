package com.jewelry;

import java.io.IOException;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;

@Configuration
public class DataConfig {
	/**
	 * SqlSessionFactoryBean格納クラス。
	 *
	 * <PRE>
	* DataSourceをSqlSessionFactoryBeanにセットします。
	* mybatisの設定情報をSqlSessionFactoryBeanにセットします。
	 * </PRE>
	 *
	 * @return SqlSessionFactoryBean。
	 */
	@Bean
	public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) throws IOException {
		SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
		factory.setDataSource(dataSource);
		ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader());
		// MyBatis のコンフィグレーションファイル
		factory.setConfigLocation(resolver.getResource("classpath:mybatis-config.xml"));

		return factory;
	}
}
