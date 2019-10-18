package com.jt.baimo.pw.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import com.jt.baimo.pw.config.properties.DruidProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.*;


/**
 * Created by yb_li on 2019/1/5.
 */
@Configuration
@Slf4j
public class DruidConfig {

    @Autowired
    private DruidProperties druidProperties;


    @Bean
    @Primary
    public DataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(druidProperties.getUrl());
        druidDataSource.setUsername(druidProperties.getUsername());
        druidDataSource.setPassword(druidProperties.getPassword());
        druidDataSource.setDriverClassName(druidProperties.getDriverClassName());

        druidDataSource.setDbType("mysql");

        druidDataSource.setInitialSize(druidProperties.getInitialSize());
        druidDataSource.setMaxActive(druidProperties.getMaxActive());
        druidDataSource.setMinIdle(druidProperties.getMinIdle());
        druidDataSource.setMaxActive(druidProperties.getMaxActive());
        druidDataSource.setTimeBetweenEvictionRunsMillis(druidProperties.getTimeBetweenEvictionRunsMillis());
        druidDataSource.setMinEvictableIdleTimeMillis(druidProperties.getMinEvictableIdleTimeMillis());
        druidDataSource.setValidationQuery(druidProperties.getValidationQuery());
        druidDataSource.setTestWhileIdle(druidProperties.isTestWhileIdle());
        druidDataSource.setTestOnBorrow(druidProperties.isTestOnBorrow());
        druidDataSource.setTestOnReturn(druidProperties.isTestOnReturn());
        druidDataSource.setPoolPreparedStatements(druidProperties.isPoolPreparedStatements());
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(druidProperties.getMaxPoolPreparedStatementPerConnectionSize());


        List filterList=new ArrayList<>();
        filterList.add(wallFilter());
        druidDataSource.setProxyFilters(filterList);
        /*  try {
            druidDataSource.setFilters(druidProperties.getFilters());
        } catch (SQLException e) {
            log.error("[druid configuration initialization filter]{}", e);
        }*/
        druidDataSource.setConnectionProperties(druidProperties.getConnectionProperties());

        return druidDataSource;
    }


    // 配置Druid的监控
    // 1.配置一个管理后台的Servelt
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String, String> initParams = new HashMap<>();

        initParams.put("loginUsername", "admin");
        initParams.put("loginPassword", "123456");
        initParams.put("allow", ""); // 默认就是允许所有访问
        initParams.put("deny", "192.168.15.21");

        bean.setInitParameters(initParams);
        return bean;
    }

    // 配置一个web监控的filter
    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());

        Map<String, String> initParams = new HashMap<>();
        initParams.put("exclusions", "*.js,*.css,/druid/*");

        bean.setInitParameters(initParams);
        bean.setUrlPatterns(Arrays.asList("/*"));

        return bean;
    }


    @Bean
    public WallFilter wallFilter(){
        WallFilter wallFilter=new WallFilter();
        wallFilter.setConfig(wallConfig());
        return wallFilter;

    }

    @Bean
    public WallConfig wallConfig(){
        WallConfig config =new WallConfig();
        config.setMultiStatementAllow(true);//允许一次执行多条语句
        config.setNoneBaseStatementAllow(true);//允许非基本语句的其他语句
        return config;
    }
}
