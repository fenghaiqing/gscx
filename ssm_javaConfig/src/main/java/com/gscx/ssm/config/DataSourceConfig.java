package com.gscx.ssm.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;


@Configuration
//启动注解
@EnableTransactionManagement
@ComponentScan(basePackages={"com.gscx.ssm.dao.*","com.gscx.ssm.service.*"})
public class DataSourceConfig implements TransactionManagementConfigurer {

	@Bean
	public DataSource configDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl(
				"jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS=(PROTOCOL=TCP)(HOST=192.168.129.155)(PORT=1521))(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME=ORCL.LK)))");
		dataSource.setUsername("income");
		dataSource.setPassword("income1234");
		dataSource.setInitialSize(10);
		dataSource.setMaxActive(200);
		dataSource.setMinIdle(1);
		dataSource.setMaxIdle(20);
		dataSource.setMaxWait(60000);
		dataSource.setDefaultAutoCommit(true);
		return dataSource;
	}

	@Bean(name="sqlSessionFactory")
	public SqlSessionFactoryBean configSqlSessionFactory() throws IOException{
		SqlSessionFactoryBean sqlSessionFactory=new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(configDataSource());
		sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:com/gscx/ssm/mapping/*.xml"));
		
		return sqlSessionFactory;
	}
	
	@Bean
	public MapperScannerConfigurer configMapperScannerConfigurer(){
		MapperScannerConfigurer  mapperScannerConfigurer=new MapperScannerConfigurer();
		mapperScannerConfigurer.setBasePackage("com/gscx/ssm/dao");
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		return mapperScannerConfigurer;
	}
	
	@Bean
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		DataSourceTransactionManager dataSourceTransactionManager= new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(configDataSource());
		return dataSourceTransactionManager;
	}

}
