package com.jt.baimo.pw.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Druid数据源属性的默认配置
 * <p>
 * Created by yb_li on 2019/1/5.
 */
@Component
@ConfigurationProperties(prefix = "spring.datasource")
@Data
public class DruidProperties {

    private String url;                 // 数据库的连接地址
    private String username;            // 数据库的用户名
    private String password;            // 数据库的密码
    private String driverClassName;     // 数据库的驱动名称

    private int initialSize = 5;        // 数据库连接池的初始化大小
    private int maxActive = 20;         // 数据库连接池的最大连接数
    private int minIdle = 5;            // 数据库连接池的最小连接数
    private int maxWait = 60000;        // 配置获取连接等待超时的时间
    private long timeBetweenEvictionRunsMillis = 60000; // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
    private long minEvictableIdleTimeMillis = 300000;   // 配置一个连接在池中最小生存的时间，单位是毫秒

    private String validationQuery = "SELECT 1 FROM DUAL";
    private boolean testWhileIdle = true;   // 建议配置为true,不影响性能，并且保证安全性。检测连接是否有效
    private boolean testOnBorrow = false;   // 申请连接时执行validationQuery检测连接是否有效，这个配置会降低性能
    private boolean testOnReturn = false;   // 归还连接时执行validationQuery检测连接是否有效，这个配置值会降低性能
    private boolean poolPreparedStatements = true;  // 打开PSCache,并且指定每个连接上PSCache的大小
    private int maxPoolPreparedStatementPerConnectionSize = 20;
    private String filters = "stat,wall";   // 配置监控统计拦截的filters，去掉后监控界面sql无法统计，‘wall’用于防火墙
    private String connectionProperties = "druid.stat.mergeSql\\=true;druid.stat.slowSqlMillis\\=5000";
}
