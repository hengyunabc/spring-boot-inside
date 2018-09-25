package com.example.mybatisdemo;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisConfig1 {

	@Bean(name = "sqlSessionFactory1")
	public SqlSessionFactory sqlSessionFactory1(DataSource dataSource1) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		org.apache.ibatis.session.Configuration ibatisConfiguration = new org.apache.ibatis.session.Configuration();
		sqlSessionFactoryBean.setConfiguration(ibatisConfiguration);

		sqlSessionFactoryBean.setDataSource(dataSource1);
		sqlSessionFactoryBean.setTypeAliasesPackage("sample.mybatis.domain");
		return sqlSessionFactoryBean.getObject();
	}

	@Bean
	MapperScannerConfigurer mapperScannerConfigurer(SqlSessionFactory sqlSessionFactory1) {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory1");
		mapperScannerConfigurer.setBasePackage("sample.mybatis.mapper");
		return mapperScannerConfigurer;
	}
}