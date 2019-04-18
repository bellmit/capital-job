package com.yixin.alixjob.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

/**
 * @description: slave数据配置
 * @autor: kevin_chen
 * @createDate: 2018/11/20 13:53
 * @version: 1.0.0
 */
@Configuration
@MapperScan(basePackages = SlaveDatasourceConfig.PACKAGE, sqlSessionFactoryRef = "slaveSqlSessionFactory")
public class SlaveDatasourceConfig {

    private static final Logger logger = LoggerFactory.getLogger(SlaveDatasourceConfig.class);

    static final String PACKAGE = "com.yixin.alixjob.mapper.slave";

    static final String MAPPER_LOCATION = "classpath:mapper/slave/**/*.xml";

    @Bean(name = "slaveDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();

        return druidDataSource;
    }

    @Bean(name = "slaveTransactionManager")
    public DataSourceTransactionManager slaveTransactionManager() {
        return new DataSourceTransactionManager(slaveDataSource());
    }

    @Bean(name = "slaveSqlSessionFactory")
    public SqlSessionFactory slaveSqlSessionFactory(@Qualifier("slaveDataSource") DataSource slaveDataSource) throws Exception {
        logger.info("从库！！！");
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(slaveDataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(SlaveDatasourceConfig.MAPPER_LOCATION));
        return sqlSessionFactoryBean.getObject();
    }
}