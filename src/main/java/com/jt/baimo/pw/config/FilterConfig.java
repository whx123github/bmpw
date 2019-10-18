package com.jt.baimo.pw.config;



import com.jt.baimo.pw.config.properties.JwtProperties;
import com.jt.baimo.pw.filter.JwtAuthFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Forever丶诺
 */
@Configuration
public class FilterConfig {


    /**
     * 用来做身份效验的jwt的Filter
     */
    @Bean
    @ConditionalOnProperty(prefix = JwtProperties.JWT_PREFIX, name = "auth-open", havingValue = "true")
    public JwtAuthFilter jwtFilter() {
        return new JwtAuthFilter();
    }
}