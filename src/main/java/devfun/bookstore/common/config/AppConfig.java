package devfun.bookstore.common.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.jolbox.bonecp.BoneCPDataSource;

@EnableTransactionManagement
@MapperScan("devfun.bookstore.common.mapper")
@ComponentScan(basePackages = {"devfun.bookstore.common.service"},
useDefaultFilters = false, includeFilters = {@Filter(Service.class)}) 
@Configuration
@PropertySource(value = { "classpath:jdbc.properties" })
public class AppConfig {
	//TODO: 데이터베이스 접속, 트랜잭션 관리, DAO, service를 정의하는 부분.
	
	@Resource
	private Environment env;
	
	// BoneCP를 사용해보자.
	@Bean(destroyMethod = "close")
	public DataSource dataSource(){
		BoneCPDataSource ds = new BoneCPDataSource();
		
		//TODO: 외부 파일 첨부하기.(properties파일)
		String driverClassName = "org.mariadb.jdbc.Driver";
		String url = "jdbc:mysql://115.71.232.94:3306/REST_APP";
		String username = "REST_ADMIN";
		String password = "REST12#$";
		
		ds.setDriverClass(env.getProperty("jdbc.driverClassName"));
		ds.setDriverClass(env.getProperty("jdbc.url"));
		ds.setDriverClass(env.getProperty("jdbc.usrename"));
		ds.setDriverClass(env.getProperty("jdbc.password"));
		return ds;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		return sessionFactory.getObject();
	}

//	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager(){
		return transactionManager(); // reference the existing @Bean method above
	}
	
}
